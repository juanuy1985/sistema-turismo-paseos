package com.turismo.mspagos.service;

import com.turismo.mspagos.client.ReservaClient;
import com.turismo.mspagos.dto.ComprobanteDTO;
import com.turismo.mspagos.dto.PagoResponse;
import com.turismo.mspagos.dto.RegistrarPagoDTO;
import com.turismo.mspagos.event.PagoConfirmadoRegistradoEvent;
import com.turismo.mspagos.dto.ReservaResponse;
import com.turismo.mspagos.exception.RecursoNoEncontradoException;
import com.turismo.mspagos.exception.ReglaNegocioException;
import com.turismo.mspagos.model.Comprobante;
import com.turismo.mspagos.model.EstadoPago;
import com.turismo.mspagos.model.Pago;
import com.turismo.mspagos.model.TipoComprobante;
import com.turismo.mspagos.repository.PagoRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagoServiceImpl implements PagoService {

    private static final String SERIE_COMPROBANTE = "B001";
    private static final DateTimeFormatter NUMERO_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final PagoRepository pagoRepository;
    private final ReservaClient reservaClient;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public PagoResponse registrarPago(RegistrarPagoDTO dto) {
        log.info("Iniciando registro de pago para reservaId={}, codigoReserva={}", dto.getReservaId(), dto.getCodigoReserva());

        // 1. Consultar reserva via Feign y validar que exista
        ReservaResponse reserva = obtenerReservaOLanzarExcepcion(dto.getReservaId());

        // 2. Validar que el código de reserva coincida
        if (!reserva.getCodigoReserva().equalsIgnoreCase(dto.getCodigoReserva())) {
            throw new ReglaNegocioException(
                    "El código de reserva '" + dto.getCodigoReserva() + "' no corresponde a la reserva con ID " + dto.getReservaId());
        }

        // 3. Validar monto y moneda contra la reserva
        if (dto.getMonto().compareTo(reserva.getMontoTotal()) != 0) {
            throw new ReglaNegocioException(
                    "El monto enviado (" + dto.getMonto() + ") no coincide con el monto de la reserva (" + reserva.getMontoTotal() + ")");
        }
        if (!dto.getMoneda().equalsIgnoreCase(reserva.getMoneda())) {
            throw new ReglaNegocioException(
                    "La moneda enviada (" + dto.getMoneda() + ") no coincide con la moneda de la reserva (" + reserva.getMoneda() + ")");
        }

        // 4. Evitar pagos duplicados: verificar si ya existe un pago CONFIRMADO para este código de reserva
        pagoRepository.findByCodigoReserva(dto.getCodigoReserva())
                .filter(p -> EstadoPago.CONFIRMADO.equals(p.getEstado()))
                .ifPresent(p -> {
                    throw new ReglaNegocioException(
                            "Ya existe un pago confirmado para la reserva con código '" + dto.getCodigoReserva() + "'");
                });

        // 5. Generar número de operación único
        String numeroOperacion = dto.getNumeroOperacion() != null && !dto.getNumeroOperacion().isBlank()
                ? dto.getNumeroOperacion()
                : generarNumeroOperacion();

        // Verificar unicidad del número de operación si fue proporcionado externamente
        pagoRepository.findByNumeroOperacion(numeroOperacion)
                .ifPresent(p -> {
                    throw new ReglaNegocioException(
                            "El número de operación '" + numeroOperacion + "' ya está registrado");
                });

        // 6. Generar comprobante asociado
        Comprobante comprobante = generarComprobante(dto.getMonto());

        // 7. Construir y guardar el pago con estado CONFIRMADO
        Pago pago = Pago.builder()
                .reservaId(dto.getReservaId())
                .codigoReserva(dto.getCodigoReserva())
                .monto(dto.getMonto())
                .moneda(dto.getMoneda().toUpperCase())
                .estado(EstadoPago.CONFIRMADO)
                .fechaPago(LocalDateTime.now())
                .metodoPago(dto.getMetodoPago())
                .numeroOperacion(numeroOperacion)
                .comprobante(comprobante)
                .build();

        Pago guardado = pagoRepository.save(pago);
        log.info("Pago registrado exitosamente con id={}, numeroOperacion={}", guardado.getId(), guardado.getNumeroOperacion());
        applicationEventPublisher.publishEvent(new PagoConfirmadoRegistradoEvent(guardado));

        return mapToResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public PagoResponse obtenerPorId(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pago no encontrado con ID: " + id));
        return mapToResponse(pago);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoResponse> listarPorReservaId(Long reservaId) {
        return pagoRepository.findByReservaId(reservaId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoResponse> listarPorCodigoReserva(String codigoReserva) {
        return pagoRepository.findAllByCodigoReserva(codigoReserva).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoResponse> listarPorEstado(EstadoPago estado) {
        return pagoRepository.findByEstado(estado).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoResponse> listarPorFecha(LocalDateTime desde, LocalDateTime hasta) {
        return pagoRepository.findByFechaPagoBetween(desde, hasta).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // --- helpers ---

    private ReservaResponse obtenerReservaOLanzarExcepcion(Long reservaId) {
        try {
            ReservaResponse reserva = reservaClient.obtenerPorId(reservaId);
            if (reserva == null) {
                throw new RecursoNoEncontradoException("No se encontró la reserva con ID: " + reservaId);
            }
            return reserva;
        } catch (FeignException.NotFound ex) {
            throw new RecursoNoEncontradoException("No se encontró la reserva con ID: " + reservaId);
        } catch (FeignException ex) {
            log.error("Error al consultar ms-reservas para reservaId={}: {}", reservaId, ex.getMessage());
            throw new ReglaNegocioException("No se pudo verificar la reserva: servicio no disponible");
        }
    }

    private String generarNumeroOperacion() {
        return "OP-" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
    }

    private Comprobante generarComprobante(java.math.BigDecimal monto) {
        String numero = LocalDateTime.now().format(NUMERO_FORMATTER) + "-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return Comprobante.builder()
                .serie(SERIE_COMPROBANTE)
                .numero(numero)
                .tipo(TipoComprobante.BOLETA)
                .fechaEmision(LocalDateTime.now())
                .montoTotal(monto)
                .build();
    }

    private PagoResponse mapToResponse(Pago pago) {
        ComprobanteDTO comprobanteDTO = null;
        if (pago.getComprobante() != null) {
            Comprobante c = pago.getComprobante();
            comprobanteDTO = ComprobanteDTO.builder()
                    .id(c.getId())
                    .serie(c.getSerie())
                    .numero(c.getNumero())
                    .tipo(c.getTipo())
                    .fechaEmision(c.getFechaEmision())
                    .montoTotal(c.getMontoTotal())
                    .build();
        }
        return PagoResponse.builder()
                .id(pago.getId())
                .reservaId(pago.getReservaId())
                .codigoReserva(pago.getCodigoReserva())
                .monto(pago.getMonto())
                .moneda(pago.getMoneda())
                .estado(pago.getEstado())
                .fechaPago(pago.getFechaPago())
                .metodoPago(pago.getMetodoPago())
                .numeroOperacion(pago.getNumeroOperacion())
                .comprobante(comprobanteDTO)
                .creadoEn(pago.getCreadoEn())
                .actualizadoEn(pago.getActualizadoEn())
                .build();
    }
}

package com.turismo.msreservas.service;

import com.turismo.msreservas.client.PaqueteClient;
import com.turismo.msreservas.config.RabbitMQConfig;
import com.turismo.msreservas.dto.*;
import com.turismo.msreservas.exception.RecursoNoEncontradoException;
import com.turismo.msreservas.model.DetalleReserva;
import com.turismo.msreservas.model.EstadoReserva;
import com.turismo.msreservas.model.Reserva;
import com.turismo.msreservas.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final PaqueteClient paqueteClient;
    private final RabbitTemplate rabbitTemplate;

    @Transactional(readOnly = true)
    public List<ReservaDTO> listarTodas() {
        return reservaRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReservaDTO obtenerPorId(Long id) {
        return reservaRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no encontrada con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<ReservaDTO> listarPorCliente(Long clienteId) {
        return reservaRepository.findByClienteId(clienteId).stream()
                .map(this::toDTO)
                .toList();
    }

    public ReservaDTO crear(CrearReservaDTO dto) {
        PaqueteDTO paquete = paqueteClient.obtenerPorId(dto.getPaqueteId());

        BigDecimal montoTotal = paquete.getPrecio().multiply(BigDecimal.valueOf(dto.getCantidadPersonas()));

        DetalleReserva detalle = DetalleReserva.builder()
                .cantidadPersonas(dto.getCantidadPersonas())
                .precioUnitario(paquete.getPrecio())
                .subtotal(montoTotal)
                .build();

        Reserva reserva = Reserva.builder()
                .clienteId(dto.getClienteId())
                .paqueteId(dto.getPaqueteId())
                .fechaReserva(dto.getFechaReserva())
                .fechaPaseo(dto.getFechaPaseo())
                .estado(EstadoReserva.PENDIENTE)
                .moneda(dto.getMoneda().toUpperCase())
                .montoTotal(montoTotal)
                .build();

        reserva.addDetalle(detalle);

        Reserva guardada = reservaRepository.save(reserva);
        log.info("Reserva creada con ID: {}", guardada.getId());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.RESERVAS_EXCHANGE,
                RabbitMQConfig.RESERVAS_ROUTING_KEY,
                toDTO(guardada));

        return toDTO(guardada);
    }

    public ReservaDTO actualizarEstado(Long id, EstadoReserva nuevoEstado) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no encontrada con ID: " + id));
        reserva.setEstado(nuevoEstado);
        return toDTO(reservaRepository.save(reserva));
    }

    public void cancelar(Long id) {
        actualizarEstado(id, EstadoReserva.CANCELADA);
        log.info("Reserva cancelada con ID: {}", id);
    }

    private ReservaDTO toDTO(Reserva reserva) {
        return ReservaDTO.builder()
                .id(reserva.getId())
                .clienteId(reserva.getClienteId())
                .paqueteId(reserva.getPaqueteId())
                .fechaReserva(reserva.getFechaReserva())
                .fechaPaseo(reserva.getFechaPaseo())
                .estado(reserva.getEstado())
                .moneda(reserva.getMoneda())
                .montoTotal(reserva.getMontoTotal())
                .codigoReserva(reserva.getCodigoReserva())
                .detalles(reserva.getDetalles().stream().map(detalle -> DetalleReservaDTO.builder()
                        .id(detalle.getId())
                        .cantidadPersonas(detalle.getCantidadPersonas())
                        .precioUnitario(detalle.getPrecioUnitario())
                        .subtotal(detalle.getSubtotal())
                        .personas(detalle.getPersonas().stream().map(persona -> PersonaReservaDTO.builder()
                                .id(persona.getId())
                                .nombres(persona.getNombres())
                                .apellidos(persona.getApellidos())
                                .tipoDocumento(persona.getTipoDocumento())
                                .numeroDocumento(persona.getNumeroDocumento())
                                .edad(persona.getEdad())
                                .build()).toList())
                        .build()).toList())
                .creadoEn(reserva.getCreadoEn())
                .actualizadoEn(reserva.getActualizadoEn())
                .build();
    }
}

package com.turismo.msreservas.service;

import com.turismo.msreservas.config.RabbitMQConfig;
import com.turismo.msreservas.dto.CrearReservaDTO;
import com.turismo.msreservas.dto.PaqueteDTO;
import com.turismo.msreservas.dto.ReservaDTO;
import com.turismo.msreservas.exception.RecursoNoEncontradoException;
import com.turismo.msreservas.client.PaqueteClient;
import com.turismo.msreservas.model.EstadoReserva;
import com.turismo.msreservas.model.Reserva;
import com.turismo.msreservas.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<ReservaDTO> listarPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toDTO)
                .toList();
    }

    public ReservaDTO crear(CrearReservaDTO dto) {
        PaqueteDTO paquete = paqueteClient.obtenerPorId(dto.getPaqueteId());

        Reserva reserva = Reserva.builder()
                .usuarioId(dto.getUsuarioId())
                .paqueteId(dto.getPaqueteId())
                .fechaReserva(dto.getFechaReserva())
                .cantidadPersonas(dto.getCantidadPersonas())
                .precioTotal(paquete.getPrecio().multiply(java.math.BigDecimal.valueOf(dto.getCantidadPersonas())))
                .estado(EstadoReserva.PENDIENTE)
                .build();

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
                .usuarioId(reserva.getUsuarioId())
                .paqueteId(reserva.getPaqueteId())
                .fechaReserva(reserva.getFechaReserva())
                .cantidadPersonas(reserva.getCantidadPersonas())
                .precioTotal(reserva.getPrecioTotal())
                .estado(reserva.getEstado())
                .creadoEn(reserva.getCreadoEn())
                .actualizadoEn(reserva.getActualizadoEn())
                .build();
    }
}

package com.turismo.msreservas.publisher;

import com.turismo.msreservas.dto.ReservaCreadaEvent;
import com.turismo.msreservas.model.Reserva;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservaCreadaPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange reservasExchange;

    @Value("${app.rabbitmq.reservas.routing-key}")
    private String reservasRoutingKey;

    public void publicar(Reserva reserva) {
        ReservaCreadaEvent evento = toEvent(reserva);
        try {
            rabbitTemplate.convertAndSend(
                    reservasExchange.getName(),
                    reservasRoutingKey,
                    evento
            );
            log.info("Evento de reserva creada publicado para reservaId={} codigo={}", evento.getReservaId(), evento.getCodigoReserva());
        } catch (RuntimeException ex) {
            log.error("Error al publicar evento de reserva creada para reservaId={} codigo={}", evento.getReservaId(), evento.getCodigoReserva(), ex);
            throw ex;
        }
    }

    private ReservaCreadaEvent toEvent(Reserva reserva) {
        return ReservaCreadaEvent.builder()
                .reservaId(reserva.getId())
                .codigoReserva(reserva.getCodigoReserva())
                .clienteId(reserva.getClienteId())
                .paqueteId(reserva.getPaqueteId())
                .montoTotal(reserva.getMontoTotal())
                .moneda(reserva.getMoneda())
                .fechaPaseo(reserva.getFechaPaseo())
                .estado(reserva.getEstado())
                .build();
    }
}

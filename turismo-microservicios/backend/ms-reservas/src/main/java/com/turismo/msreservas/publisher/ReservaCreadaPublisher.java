package com.turismo.msreservas.publisher;

import com.turismo.msreservas.dto.ReservaCreadaEvent;
import com.turismo.msreservas.model.Reserva;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservaCreadaPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange reservasExchange;

    @Value("${app.rabbitmq.reservas.routing-key}")
    private String reservasRoutingKey;

    public void publicar(Reserva reserva) {
        rabbitTemplate.convertAndSend(
                reservasExchange.getName(),
                reservasRoutingKey,
                toEvent(reserva)
        );
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

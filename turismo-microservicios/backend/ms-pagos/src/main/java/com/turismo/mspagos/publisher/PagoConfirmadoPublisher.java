package com.turismo.mspagos.publisher;

import com.turismo.mspagos.dto.PagoConfirmadoEvent;
import com.turismo.mspagos.model.Pago;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PagoConfirmadoPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange pagosExchange;

    @Value("${app.rabbitmq.pagos.routing-key}")
    private String pagosRoutingKey;

    public void publicar(Pago pago) {
        PagoConfirmadoEvent evento = toEvent(pago);
        try {
            rabbitTemplate.convertAndSend(
                    pagosExchange.getName(),
                    pagosRoutingKey,
                    evento
            );
            log.info("Evento de pago confirmado publicado para pagoId={} reservaId={}",
                    evento.getPagoId(), evento.getReservaId());
        } catch (AmqpException ex) {
            log.error("Error al publicar evento de pago confirmado para pagoId={} reservaId={}",
                    evento.getPagoId(), evento.getReservaId(), ex);
            throw ex;
        }
    }

    private PagoConfirmadoEvent toEvent(Pago pago) {
        return PagoConfirmadoEvent.builder()
                .pagoId(pago.getId())
                .reservaId(pago.getReservaId())
                .codigoReserva(pago.getCodigoReserva())
                .monto(pago.getMonto())
                .moneda(pago.getMoneda())
                .estado(pago.getEstado())
                .numeroOperacion(pago.getNumeroOperacion())
                .fechaPago(pago.getFechaPago())
                .build();
    }
}

package com.turismo.msnotificaciones.listener;

import com.turismo.msnotificaciones.dto.PagoConfirmadoEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PagosEventListener {

    @RabbitListener(queues = "${app.rabbitmq.pagos.queue}")
    public void onPagoConfirmado(PagoConfirmadoEventDto event) {
        if (event == null) {
            log.warn("Evento pago.confirmado recibido sin payload");
            return;
        }
        log.info("Evento pago.confirmado recibido: pagoId={} reservaId={}", event.getPagoId(), event.getReservaId());
    }
}

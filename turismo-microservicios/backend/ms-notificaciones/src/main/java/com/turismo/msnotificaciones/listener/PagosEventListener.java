package com.turismo.msnotificaciones.listener;

import com.turismo.msnotificaciones.dto.PagoConfirmadoEventDto;
import com.turismo.msnotificaciones.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PagosEventListener {
    private final NotificacionService notificacionService;

    @RabbitListener(queues = "${app.rabbitmq.pagos.queue}")
    public void onPagoConfirmado(PagoConfirmadoEventDto event) {
        if (event == null) {
            log.warn("Evento pago.confirmado recibido sin payload");
            return;
        }
        notificacionService.notificarPagoConfirmado(event);
    }
}

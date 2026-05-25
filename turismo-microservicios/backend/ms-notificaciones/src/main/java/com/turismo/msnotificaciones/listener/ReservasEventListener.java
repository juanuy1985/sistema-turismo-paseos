package com.turismo.msnotificaciones.listener;

import com.turismo.msnotificaciones.dto.ReservaCreadaEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReservasEventListener {

    @RabbitListener(queues = "${app.rabbitmq.reservas.queue}")
    public void onReservaCreada(ReservaCreadaEventDto event) {
        if (event == null) {
            log.warn("Evento reserva.creada recibido sin payload");
            return;
        }
        log.info("Evento reserva.creada recibido: reservaId={} codigoReserva={}", event.getReservaId(), event.getCodigoReserva());
    }
}

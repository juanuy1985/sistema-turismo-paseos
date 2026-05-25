package com.turismo.msnotificaciones.service;

import com.turismo.msnotificaciones.dto.PagoConfirmadoEventDto;
import com.turismo.msnotificaciones.dto.ReservaCreadaEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionService {

    public void notificarReservaCreada(ReservaCreadaEventDto event) {
        if (event == null) {
            log.warn("No se puede simular notificación de reserva creada: payload nulo");
            return;
        }
        log.info(
                "Simulación notificación reserva creada: codigoReserva={}, clienteId={}, paqueteId={}, fechaPaseo={}, monto={}",
                event.getCodigoReserva(),
                event.getClienteId(),
                event.getPaqueteId(),
                event.getFechaPaseo(),
                event.getMontoTotal()
        );
    }

    public void notificarPagoConfirmado(PagoConfirmadoEventDto event) {
        if (event == null) {
            log.warn("No se puede simular notificación de pago confirmado: payload nulo");
            return;
        }
        log.info(
                "Simulación notificación pago confirmado: codigoReserva={}, numeroOperacion={}, monto={}, moneda={}, estado={}",
                event.getCodigoReserva(),
                event.getNumeroOperacion(),
                event.getMonto(),
                event.getMoneda(),
                event.getEstado()
        );
    }
}

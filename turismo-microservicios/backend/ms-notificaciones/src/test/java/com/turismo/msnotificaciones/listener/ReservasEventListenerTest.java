package com.turismo.msnotificaciones.listener;

import com.turismo.msnotificaciones.dto.ReservaCreadaEventDto;
import com.turismo.msnotificaciones.service.NotificacionService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class ReservasEventListenerTest {

    private final NotificacionService notificacionService = mock(NotificacionService.class);
    private final ReservasEventListener listener = new ReservasEventListener(notificacionService);

    @Test
    void onReservaCreadaNoFallaConPayloadNulo() {
        assertDoesNotThrow(() -> listener.onReservaCreada(null));
        verify(notificacionService, never()).notificarReservaCreada(any());
    }

    @Test
    void onReservaCreadaNoFallaConPayloadValido() {
        ReservaCreadaEventDto evento = new ReservaCreadaEventDto();
        evento.setReservaId(1L);
        evento.setCodigoReserva("RES-001");

        assertDoesNotThrow(() -> listener.onReservaCreada(evento));
        verify(notificacionService).notificarReservaCreada(evento);
    }
}

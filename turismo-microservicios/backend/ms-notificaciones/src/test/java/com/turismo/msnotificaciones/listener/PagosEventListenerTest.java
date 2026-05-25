package com.turismo.msnotificaciones.listener;

import com.turismo.msnotificaciones.dto.PagoConfirmadoEventDto;
import com.turismo.msnotificaciones.service.NotificacionService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class PagosEventListenerTest {

    private final NotificacionService notificacionService = mock(NotificacionService.class);
    private final PagosEventListener listener = new PagosEventListener(notificacionService);

    @Test
    void onPagoConfirmadoNoFallaConPayloadNulo() {
        assertDoesNotThrow(() -> listener.onPagoConfirmado(null));
        verify(notificacionService, never()).notificarPagoConfirmado(any());
    }

    @Test
    void onPagoConfirmadoNoFallaConPayloadValido() {
        PagoConfirmadoEventDto evento = new PagoConfirmadoEventDto();
        evento.setPagoId(1L);
        evento.setReservaId(10L);

        assertDoesNotThrow(() -> listener.onPagoConfirmado(evento));
        verify(notificacionService).notificarPagoConfirmado(evento);
    }
}

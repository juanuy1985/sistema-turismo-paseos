package com.turismo.msnotificaciones.listener;

import com.turismo.msnotificaciones.dto.PagoConfirmadoEventDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PagosEventListenerTest {

    private final PagosEventListener listener = new PagosEventListener();

    @Test
    void onPagoConfirmadoNoFallaConPayloadNulo() {
        assertDoesNotThrow(() -> listener.onPagoConfirmado(null));
    }

    @Test
    void onPagoConfirmadoNoFallaConPayloadValido() {
        PagoConfirmadoEventDto evento = new PagoConfirmadoEventDto();
        evento.setPagoId(1L);
        evento.setReservaId(10L);

        assertDoesNotThrow(() -> listener.onPagoConfirmado(evento));
    }
}

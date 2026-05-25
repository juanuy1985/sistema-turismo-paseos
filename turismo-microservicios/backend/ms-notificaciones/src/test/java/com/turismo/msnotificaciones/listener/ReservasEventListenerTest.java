package com.turismo.msnotificaciones.listener;

import com.turismo.msnotificaciones.dto.ReservaCreadaEventDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ReservasEventListenerTest {

    private final ReservasEventListener listener = new ReservasEventListener();

    @Test
    void onReservaCreadaNoFallaConPayloadNulo() {
        assertDoesNotThrow(() -> listener.onReservaCreada(null));
    }

    @Test
    void onReservaCreadaNoFallaConPayloadValido() {
        ReservaCreadaEventDto evento = new ReservaCreadaEventDto();
        evento.setReservaId(1L);
        evento.setCodigoReserva("RES-001");

        assertDoesNotThrow(() -> listener.onReservaCreada(evento));
    }
}

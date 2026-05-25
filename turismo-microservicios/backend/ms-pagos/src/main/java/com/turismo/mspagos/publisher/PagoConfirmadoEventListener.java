package com.turismo.mspagos.publisher;

import com.turismo.mspagos.event.PagoConfirmadoRegistradoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class PagoConfirmadoEventListener {

    private final PagoConfirmadoPublisher pagoConfirmadoPublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPagoConfirmadoRegistrado(PagoConfirmadoRegistradoEvent event) {
        try {
            pagoConfirmadoPublisher.publicar(event.pago());
        } catch (RuntimeException ex) {
            log.error("Pago confirmado persistido para pagoId={}, pero no se pudo publicar el evento RabbitMQ", event.pago().getId(), ex);
        }
    }
}

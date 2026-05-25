import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Notificacion } from '../../../models/notificacion.model';
import { Pago, MetodoPago } from '../../../models/pago.model';
import { PagosService } from '../../../services/pagos.service';
import { FlujoNotificacionesService } from '../../../features/flujo/flujo-notificaciones.service';

@Component({
  selector: 'app-pago-procesar',
  templateUrl: './pago-procesar.component.html',
  styleUrl: './pago-procesar.component.scss'
})
export class PagoProcesarComponent {
  readonly metodosPago: MetodoPago[] = ['TARJETA', 'TRANSFERENCIA', 'EFECTIVO', 'BILLETERA_DIGITAL'];

  loading = false;
  errorMessage = '';
  successMessage = '';
  pagoProcesado: Pago | null = null;

  readonly form = this.fb.nonNullable.group({
    reservaId: [Number(localStorage.getItem('turismo-ultima-reserva-id') ?? '0'), [Validators.required, Validators.min(1)]],
    codigoReserva: [localStorage.getItem('turismo-ultimo-codigo-reserva') ?? '', [Validators.required]],
    monto: [Number(localStorage.getItem('turismo-ultimo-monto-reserva') ?? '0'), [Validators.required, Validators.min(0.01)]],
    moneda: [localStorage.getItem('turismo-ultima-moneda-reserva') ?? 'PEN', [Validators.required, Validators.minLength(3), Validators.maxLength(3)]],
    metodoPago: ['TARJETA' as MetodoPago, [Validators.required]],
    numeroOperacion: ['']
  });

  constructor(
    private readonly fb: FormBuilder,
    private readonly pagosService: PagosService,
    private readonly flujoNotificacionesService: FlujoNotificacionesService
  ) {}

  procesarPago(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';

    const payload = this.form.getRawValue();

    this.pagosService.procesarPago({ ...payload, moneda: payload.moneda.toUpperCase() }).subscribe({
      next: (pago) => {
        this.loading = false;
        this.pagoProcesado = pago;
        this.successMessage = `Pago procesado correctamente para la reserva ${pago.codigoReserva}.`;

        const notificacion: Notificacion = {
          id: crypto.randomUUID(),
          tipo: 'SUCCESS',
          titulo: 'Pago confirmado',
          mensaje: `Pago ${pago.id} confirmado para reserva ${pago.codigoReserva}.`,
          fecha: new Date().toISOString()
        };

        this.flujoNotificacionesService.agregar(notificacion);
      },
      error: () => {
        this.loading = false;
        this.errorMessage = 'No se pudo procesar el pago. Intenta nuevamente.';
      }
    });
  }
}

import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Reserva } from '../../../models/reserva.model';
import { ReservasService } from '../../../services/reservas.service';

@Component({
  selector: 'app-reserva-consulta',
  templateUrl: './reserva-consulta.component.html',
  styleUrl: './reserva-consulta.component.scss'
})
export class ReservaConsultaComponent {
  loading = false;
  errorMessage = '';
  reservas: Reserva[] = [];

  readonly formCodigo = this.fb.nonNullable.group({
    codigoReserva: ['', [Validators.required]]
  });

  readonly formCliente = this.fb.nonNullable.group({
    clienteId: [0, [Validators.required, Validators.min(1)]]
  });

  constructor(
    private readonly fb: FormBuilder,
    private readonly reservasService: ReservasService
  ) {
    const codigo = localStorage.getItem('turismo-ultimo-codigo-reserva');
    const clienteId = localStorage.getItem('turismo-ultimo-cliente-id');
    if (codigo) {
      this.formCodigo.controls.codigoReserva.setValue(codigo);
    }
    if (clienteId) {
      this.formCliente.controls.clienteId.setValue(Number(clienteId));
    }
  }

  consultarPorCodigo(): void {
    if (this.formCodigo.invalid) {
      this.formCodigo.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    this.reservasService.buscarPorCodigo(this.formCodigo.controls.codigoReserva.value).subscribe({
      next: (reserva) => {
        this.reservas = [reserva];
        this.loading = false;
      },
      error: () => {
        this.reservas = [];
        this.loading = false;
        this.errorMessage = 'No se encontró una reserva con ese código.';
      }
    });
  }

  consultarPorCliente(): void {
    if (this.formCliente.invalid) {
      this.formCliente.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    this.reservasService.listarPorCliente(this.formCliente.controls.clienteId.value).subscribe({
      next: (reservas) => {
        this.reservas = reservas;
        this.loading = false;
      },
      error: () => {
        this.reservas = [];
        this.loading = false;
        this.errorMessage = 'No se pudieron consultar reservas por cliente.';
      }
    });
  }
}

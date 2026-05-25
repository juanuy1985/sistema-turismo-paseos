import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Cliente } from '../../../models/cliente.model';
import { ClientesService } from '../../../services/clientes.service';

@Component({
    selector: 'app-cliente-registro',
    templateUrl: './cliente-registro.component.html',
    styleUrl: './cliente-registro.component.scss',
    standalone: false
})
export class ClienteRegistroComponent {
  loading = false;
  errorMessage = '';
  successMessage = '';
  clienteCreado: Cliente | null = null;

  readonly form = this.fb.nonNullable.group({
    nombres: ['', [Validators.required, Validators.maxLength(100)]],
    apellidos: ['', [Validators.required, Validators.maxLength(100)]],
    email: ['', [Validators.required, Validators.email]],
    telefono: ['', [Validators.required, Validators.pattern(/^\+?[0-9]{7,20}$/)]],
    username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
    password: ['', [Validators.required, Validators.minLength(8)]],
    tipoDocumento: ['', [Validators.required]],
    numeroDocumento: ['', [Validators.required]],
    direccion: ['', [Validators.required, Validators.maxLength(200)]]
  });

  constructor(
    private readonly fb: FormBuilder,
    private readonly clientesService: ClientesService
  ) {}

  registrar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';

    this.clientesService.registrarCliente(this.form.getRawValue()).subscribe({
      next: (cliente) => {
        this.clienteCreado = cliente;
        this.loading = false;
        this.successMessage = `Cliente registrado correctamente con ID ${cliente.id}.`;
        localStorage.setItem('turismo-ultimo-cliente-id', String(cliente.id));
        localStorage.setItem('turismo-ultimo-cliente-documento', cliente.numeroDocumento);
        this.form.reset();
      },
      error: () => {
        this.loading = false;
        this.errorMessage = 'No se pudo registrar el cliente. Verifica los datos.';
      }
    });
  }
}

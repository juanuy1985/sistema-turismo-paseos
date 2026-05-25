import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Paquete } from '../../../models/paquete.model';
import { Reserva } from '../../../models/reserva.model';
import { Notificacion } from '../../../models/notificacion.model';
import { ClientesService } from '../../../services/clientes.service';
import { PaquetesService } from '../../../services/paquetes.service';
import { ReservasService } from '../../../services/reservas.service';
import { FlujoNotificacionesService } from '../../../features/flujo/flujo-notificaciones.service';

@Component({
    selector: 'app-reserva-crear',
    templateUrl: './reserva-crear.component.html',
    styleUrl: './reserva-crear.component.scss',
    standalone: false
})
export class ReservaCrearComponent implements OnInit {
  paquetes: Paquete[] = [];
  loading = false;
  searchingCliente = false;
  successMessage = '';
  errorMessage = '';
  reservaCreada: Reserva | null = null;

  readonly form = this.fb.nonNullable.group({
    clienteDocumento: ['', [Validators.required]],
    clienteId: [0, [Validators.required, Validators.min(1)]],
    paqueteId: [0, [Validators.required, Validators.min(1)]],
    fechaPaseo: ['', [Validators.required]],
    cantidadPersonas: [1, [Validators.required, Validators.min(1)]],
    nombresTitular: ['', [Validators.required]],
    apellidosTitular: ['', [Validators.required]],
    tipoDocumentoTitular: ['DNI', [Validators.required]],
    numeroDocumentoTitular: ['', [Validators.required]],
    edadTitular: [18, [Validators.required, Validators.min(0), Validators.max(120)]],
    moneda: ['PEN', [Validators.required, Validators.minLength(3), Validators.maxLength(3)]]
  });

  constructor(
    private readonly fb: FormBuilder,
    private readonly paquetesService: PaquetesService,
    private readonly clientesService: ClientesService,
    private readonly reservasService: ReservasService,
    private readonly flujoNotificacionesService: FlujoNotificacionesService
  ) {}

  ngOnInit(): void {
    this.cargarPaquetes();

    const ultimoClienteId = localStorage.getItem('turismo-ultimo-cliente-id');
    const ultimoDocumento = localStorage.getItem('turismo-ultimo-cliente-documento');
    if (ultimoClienteId) {
      this.form.controls.clienteId.setValue(Number(ultimoClienteId));
    }
    if (ultimoDocumento) {
      this.form.controls.clienteDocumento.setValue(ultimoDocumento);
    }
  }

  cargarPaquetes(): void {
    this.paquetesService.listarPaquetes().subscribe({
      next: (paquetes) => {
        this.paquetes = paquetes;
      },
      error: () => {
        this.errorMessage = 'No se pudieron cargar los paquetes disponibles.';
      }
    });
  }

  buscarClientePorDocumento(): void {
    const documento = this.form.controls.clienteDocumento.value.trim();
    if (!documento) {
      this.errorMessage = 'Ingresa el número de documento para buscar cliente.';
      return;
    }

    this.searchingCliente = true;
    this.errorMessage = '';

    this.clientesService.buscarPorDocumento(documento).subscribe({
      next: (cliente) => {
        this.form.controls.clienteId.setValue(cliente.id);
        this.searchingCliente = false;
      },
      error: () => {
        this.searchingCliente = false;
        this.errorMessage = 'No se encontró cliente con ese documento.';
      }
    });
  }

  crearReserva(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.successMessage = '';
    this.errorMessage = '';

    const value = this.form.getRawValue();
    const fechaPaseo = new Date(value.fechaPaseo);
    const fechaPaseoIso = fechaPaseo.toISOString().slice(0, 10);
    const fechaReservaIso = new Date().toISOString().slice(0, 10);

    const personas = [
      {
        nombres: value.nombresTitular,
        apellidos: value.apellidosTitular,
        tipoDocumento: value.tipoDocumentoTitular,
        numeroDocumento: value.numeroDocumentoTitular,
        edad: value.edadTitular
      }
    ];

    for (let i = 1; i < value.cantidadPersonas; i += 1) {
      personas.push({
        nombres: `Acompañante ${i}`,
        apellidos: value.apellidosTitular,
        tipoDocumento: value.tipoDocumentoTitular,
        numeroDocumento: `${value.numeroDocumentoTitular}-${i}`,
        edad: value.edadTitular
      });
    }

    this.reservasService
      .crearReserva({
        clienteId: value.clienteId,
        paqueteId: value.paqueteId,
        fechaReserva: fechaReservaIso,
        fechaPaseo: fechaPaseoIso,
        moneda: value.moneda.toUpperCase(),
        cantidadPersonas: value.cantidadPersonas,
        personas
      })
      .subscribe({
        next: (reserva) => {
          this.reservaCreada = reserva;
          this.loading = false;
          this.successMessage = `Reserva creada con código ${reserva.codigoReserva}.`;
          localStorage.setItem('turismo-ultima-reserva-id', String(reserva.id));
          localStorage.setItem('turismo-ultimo-codigo-reserva', reserva.codigoReserva);
          localStorage.setItem('turismo-ultimo-monto-reserva', String(reserva.montoTotal));
          localStorage.setItem('turismo-ultima-moneda-reserva', reserva.moneda);
          const notificacion: Notificacion = {
            id: crypto.randomUUID(),
            tipo: 'SUCCESS',
            titulo: 'Reserva creada',
            mensaje: `Reserva ${reserva.codigoReserva} registrada correctamente.`,
            fecha: new Date().toISOString()
          };
          this.flujoNotificacionesService.agregar(notificacion);
        },
        error: () => {
          this.loading = false;
          this.errorMessage = 'No se pudo crear la reserva. Revisa los datos ingresados.';
        }
      });
  }
}

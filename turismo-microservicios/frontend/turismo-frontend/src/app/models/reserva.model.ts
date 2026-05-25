export type EstadoReserva = 'PENDIENTE' | 'CONFIRMADA' | 'CANCELADA' | 'COMPLETADA';

export interface PersonaReserva {
  nombres: string;
  apellidos: string;
  tipoDocumento: string;
  numeroDocumento: string;
  edad: number;
}

export interface DetalleReserva {
  id: number;
  cantidadPersonas: number;
  precioUnitario: number;
  subtotal: number;
  personas: PersonaReserva[];
}

export interface Reserva {
  id: number;
  clienteId: number;
  paqueteId: number;
  fechaReserva: string;
  fechaPaseo: string;
  estado: EstadoReserva;
  moneda: string;
  montoTotal: number;
  codigoReserva: string;
  detalles: DetalleReserva[];
  creadoEn?: string;
  actualizadoEn?: string;
}

export interface CrearReservaRequest {
  clienteId: number;
  paqueteId: number;
  fechaReserva: string;
  fechaPaseo: string;
  moneda: string;
  cantidadPersonas: number;
  personas: PersonaReserva[];
}

import { Comprobante } from './comprobante.model';

export type MetodoPago = 'TARJETA' | 'TRANSFERENCIA' | 'EFECTIVO' | 'BILLETERA_DIGITAL';
export type EstadoPago = 'PENDIENTE' | 'CONFIRMADO' | 'PROCESADO' | 'RECHAZADO' | 'REEMBOLSADO';

export interface Pago {
  id: number;
  reservaId: number;
  codigoReserva: string;
  monto: number;
  moneda: string;
  estado: EstadoPago;
  fechaPago: string;
  metodoPago: MetodoPago;
  numeroOperacion?: string;
  comprobante: Comprobante;
  creadoEn?: string;
  actualizadoEn?: string;
}

export interface RegistrarPagoRequest {
  reservaId: number;
  codigoReserva: string;
  monto: number;
  moneda: string;
  metodoPago: MetodoPago;
  numeroOperacion?: string;
}

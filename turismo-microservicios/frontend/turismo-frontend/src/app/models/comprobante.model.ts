export type TipoComprobante = 'BOLETA' | 'FACTURA' | 'RECIBO';

export interface Comprobante {
  id: number;
  serie: string;
  numero: string;
  tipo: TipoComprobante;
  fechaEmision: string;
  montoTotal: number;
}

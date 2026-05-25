export interface Paquete {
  id: number;
  titulo: string;
  descripcion: string;
  destinoId: number;
  destinoNombre: string;
  tipoPaseoId: number;
  tipoPaseoNombre: string;
  precio: number;
  moneda: string;
  duracionDias: number;
  cuposDisponibles: number;
  cuposReservados: number;
  cuposActuales: number;
  estadoActivo: boolean;
  fechaCreacion?: string;
  fechaActualizacion?: string;
}

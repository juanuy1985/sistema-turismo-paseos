export type TipoNotificacion = 'INFO' | 'SUCCESS' | 'WARNING';

export interface Notificacion {
  id: string;
  tipo: TipoNotificacion;
  titulo: string;
  mensaje: string;
  fecha: string;
}

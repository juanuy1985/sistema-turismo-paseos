import { Injectable } from '@angular/core';
import { Notificacion } from '../../models/notificacion.model';

@Injectable({
  providedIn: 'root'
})
export class FlujoNotificacionesService {
  private readonly storageKey = 'turismo-notificaciones';

  obtener(): Notificacion[] {
    const raw = localStorage.getItem(this.storageKey);
    if (!raw) {
      return [];
    }

    try {
      return JSON.parse(raw) as Notificacion[];
    } catch {
      return [];
    }
  }

  agregar(notificacion: Notificacion): void {
    const actuales = this.obtener();
    const nuevas = [notificacion, ...actuales].slice(0, 20);
    localStorage.setItem(this.storageKey, JSON.stringify(nuevas));
  }
}

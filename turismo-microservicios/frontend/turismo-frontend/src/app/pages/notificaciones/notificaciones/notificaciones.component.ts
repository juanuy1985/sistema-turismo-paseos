import { Component, OnInit } from '@angular/core';
import { FlujoNotificacionesService } from '../../../features/flujo/flujo-notificaciones.service';
import { Notificacion } from '../../../models/notificacion.model';

@Component({
  selector: 'app-notificaciones',
  templateUrl: './notificaciones.component.html',
  styleUrl: './notificaciones.component.scss'
})
export class NotificacionesComponent implements OnInit {
  notificaciones: Notificacion[] = [];

  constructor(private readonly flujoNotificacionesService: FlujoNotificacionesService) {}

  ngOnInit(): void {
    this.refrescar();
  }

  refrescar(): void {
    this.notificaciones = this.flujoNotificacionesService.obtener();
  }

  simularNotificacion(): void {
    this.flujoNotificacionesService.agregar({
      id: crypto.randomUUID(),
      tipo: 'INFO',
      titulo: 'Notificación simulada',
      mensaje: 'Esta es una notificación de prueba para validar el flujo completo.',
      fecha: new Date().toISOString()
    });
    this.refrescar();
  }
}

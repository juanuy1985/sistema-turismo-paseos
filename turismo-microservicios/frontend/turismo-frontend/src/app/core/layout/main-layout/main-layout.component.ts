import { Component } from '@angular/core';

interface MenuItem {
  path: string;
  label: string;
}

@Component({
    selector: 'app-main-layout',
    templateUrl: './main-layout.component.html',
    styleUrl: './main-layout.component.scss',
    standalone: false
})
export class MainLayoutComponent {
  readonly menu: MenuItem[] = [
    { path: '/paquetes', label: 'Paquetes' },
    { path: '/clientes/registro', label: 'Clientes' },
    { path: '/reservas/nueva', label: 'Reservas' },
    { path: '/pagos/procesar', label: 'Pagos' },
    { path: '/notificaciones', label: 'Notificaciones' }
  ];
}

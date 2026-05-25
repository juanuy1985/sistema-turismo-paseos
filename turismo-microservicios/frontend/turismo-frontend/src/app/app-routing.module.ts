import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainLayoutComponent } from './core/layout/main-layout/main-layout.component';
import { PaquetesListComponent } from './pages/paquetes/paquetes-list/paquetes-list.component';
import { PaqueteDetailComponent } from './pages/paquetes/paquete-detail/paquete-detail.component';
import { ClienteRegistroComponent } from './pages/clientes/cliente-registro/cliente-registro.component';
import { ReservaCrearComponent } from './pages/reservas/reserva-crear/reserva-crear.component';
import { ReservaConsultaComponent } from './pages/reservas/reserva-consulta/reserva-consulta.component';
import { PagoProcesarComponent } from './pages/pagos/pago-procesar/pago-procesar.component';
import { NotificacionesComponent } from './pages/notificaciones/notificaciones/notificaciones.component';

const routes: Routes = [
  {
    path: '',
    component: MainLayoutComponent,
    children: [
      { path: '', redirectTo: 'paquetes', pathMatch: 'full' },
      { path: 'paquetes', component: PaquetesListComponent },
      { path: 'paquetes/:id', component: PaqueteDetailComponent },
      { path: 'clientes/registro', component: ClienteRegistroComponent },
      { path: 'reservas/nueva', component: ReservaCrearComponent },
      { path: 'reservas/consulta', component: ReservaConsultaComponent },
      { path: 'pagos/procesar', component: PagoProcesarComponent },
      { path: 'notificaciones', component: NotificacionesComponent }
    ]
  },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}

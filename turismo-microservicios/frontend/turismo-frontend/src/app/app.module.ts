import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainLayoutComponent } from './core/layout/main-layout/main-layout.component';
import { PaquetesListComponent } from './pages/paquetes/paquetes-list/paquetes-list.component';
import { PaqueteDetailComponent } from './pages/paquetes/paquete-detail/paquete-detail.component';
import { ClienteRegistroComponent } from './pages/clientes/cliente-registro/cliente-registro.component';
import { ReservaCrearComponent } from './pages/reservas/reserva-crear/reserva-crear.component';
import { ReservaConsultaComponent } from './pages/reservas/reserva-consulta/reserva-consulta.component';
import { PagoProcesarComponent } from './pages/pagos/pago-procesar/pago-procesar.component';
import { NotificacionesComponent } from './pages/notificaciones/notificaciones/notificaciones.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { httpErrorInterceptor } from './core/interceptors/http-error.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    MainLayoutComponent,
    PaquetesListComponent,
    PaqueteDetailComponent,
    ClienteRegistroComponent,
    ReservaCrearComponent,
    ReservaConsultaComponent,
    PagoProcesarComponent,
    NotificacionesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    MatToolbarModule,
    MatButtonModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatSnackBarModule,
    MatIconModule,
    MatChipsModule
  ],
  providers: [
    provideAnimationsAsync(),
    provideHttpClient(withInterceptors([httpErrorInterceptor]))
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}

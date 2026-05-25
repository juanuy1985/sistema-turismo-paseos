import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CrearReservaRequest, Reserva } from '../models/reserva.model';

@Injectable({
  providedIn: 'root'
})
export class ReservasService {
  private readonly api = '/api/reservas';

  constructor(private readonly http: HttpClient) {}

  crearReserva(payload: CrearReservaRequest): Observable<Reserva> {
    return this.http.post<Reserva>(this.api, payload);
  }

  buscarPorCodigo(codigo: string): Observable<Reserva> {
    return this.http.get<Reserva>(`${this.api}/codigo/${codigo}`);
  }

  listarPorCliente(clienteId: number): Observable<Reserva[]> {
    return this.http.get<Reserva[]>(`${this.api}/cliente/${clienteId}`);
  }
}

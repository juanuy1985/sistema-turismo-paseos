import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pago, RegistrarPagoRequest } from '../models/pago.model';

@Injectable({
  providedIn: 'root'
})
export class PagosService {
  private readonly api = '/api/pagos';

  constructor(private readonly http: HttpClient) {}

  procesarPago(payload: RegistrarPagoRequest): Observable<Pago> {
    return this.http.post<Pago>(this.api, payload);
  }

  listarPorCodigoReserva(codigoReserva: string): Observable<Pago[]> {
    return this.http.get<Pago[]>(`${this.api}/codigo-reserva/${codigoReserva}`);
  }
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cliente, ClienteRegistro } from '../models/cliente.model';

@Injectable({
  providedIn: 'root'
})
export class ClientesService {
  private readonly api = '/api/clientes';

  constructor(private readonly http: HttpClient) {}

  registrarCliente(payload: ClienteRegistro): Observable<Cliente> {
    return this.http.post<Cliente>(this.api, payload);
  }

  buscarPorDocumento(numeroDocumento: string): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.api}/documento/${numeroDocumento}`);
  }
}

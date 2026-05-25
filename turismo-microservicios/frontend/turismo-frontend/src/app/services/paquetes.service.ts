import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Paquete } from '../models/paquete.model';

@Injectable({
  providedIn: 'root'
})
export class PaquetesService {
  private readonly api = '/api/paquetes';

  constructor(private readonly http: HttpClient) {}

  listarPaquetes(): Observable<Paquete[]> {
    return this.http.get<Paquete[]>(this.api);
  }

  obtenerPaquete(id: number): Observable<Paquete> {
    return this.http.get<Paquete>(`${this.api}/${id}`);
  }
}

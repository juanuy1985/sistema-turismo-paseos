import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Paquete } from '../../../models/paquete.model';
import { PaquetesService } from '../../../services/paquetes.service';

@Component({
  selector: 'app-paquetes-list',
  templateUrl: './paquetes-list.component.html',
  styleUrl: './paquetes-list.component.scss'
})
export class PaquetesListComponent implements OnInit {
  paquetes: Paquete[] = [];
  loading = false;
  errorMessage = '';

  constructor(
    private readonly paquetesService: PaquetesService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.cargarPaquetes();
  }

  cargarPaquetes(): void {
    this.loading = true;
    this.errorMessage = '';

    this.paquetesService.listarPaquetes().subscribe({
      next: (data) => {
        this.paquetes = data;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'No se pudieron cargar los paquetes turísticos.';
        this.loading = false;
      }
    });
  }

  verDetalle(id: number): void {
    this.router.navigate(['/paquetes', id]);
  }
}

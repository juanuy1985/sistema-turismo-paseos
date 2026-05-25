import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Paquete } from '../../../models/paquete.model';
import { PaquetesService } from '../../../services/paquetes.service';

@Component({
  selector: 'app-paquete-detail',
  templateUrl: './paquete-detail.component.html',
  styleUrl: './paquete-detail.component.scss'
})
export class PaqueteDetailComponent implements OnInit {
  paquete: Paquete | null = null;
  loading = false;
  errorMessage = '';

  constructor(
    private readonly route: ActivatedRoute,
    private readonly paquetesService: PaquetesService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    const id = Number(idParam);

    if (!idParam || Number.isNaN(id)) {
      this.errorMessage = 'ID de paquete inválido.';
      return;
    }

    this.loading = true;
    this.paquetesService.obtenerPaquete(id).subscribe({
      next: (data) => {
        this.paquete = data;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'No se pudo obtener el detalle del paquete.';
        this.loading = false;
      }
    });
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlertaFerramenta } from 'app/shared/model/alerta-ferramenta.model';

@Component({
  selector: 'jhi-alerta-ferramenta-detail',
  templateUrl: './alerta-ferramenta-detail.component.html',
})
export class AlertaFerramentaDetailComponent implements OnInit {
  alertaFerramenta: IAlertaFerramenta | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alertaFerramenta }) => (this.alertaFerramenta = alertaFerramenta));
  }

  previousState(): void {
    window.history.back();
  }
}

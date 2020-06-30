import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlertaRisco } from 'app/shared/model/alerta-risco.model';

@Component({
  selector: 'jhi-alerta-risco-detail',
  templateUrl: './alerta-risco-detail.component.html',
})
export class AlertaRiscoDetailComponent implements OnInit {
  alertaRisco: IAlertaRisco | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alertaRisco }) => (this.alertaRisco = alertaRisco));
  }

  previousState(): void {
    window.history.back();
  }
}

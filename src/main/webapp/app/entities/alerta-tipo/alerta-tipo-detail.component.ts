import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlertaTipo } from 'app/shared/model/alerta-tipo.model';

@Component({
  selector: 'jhi-alerta-tipo-detail',
  templateUrl: './alerta-tipo-detail.component.html',
})
export class AlertaTipoDetailComponent implements OnInit {
  alertaTipo: IAlertaTipo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alertaTipo }) => (this.alertaTipo = alertaTipo));
  }

  previousState(): void {
    window.history.back();
  }
}

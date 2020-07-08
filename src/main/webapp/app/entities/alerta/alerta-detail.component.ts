import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlerta } from 'app/shared/model/alerta.model';

@Component({
  selector: 'jhi-alerta-detail',
  templateUrl: './alerta-detail.component.html',
})
export class AlertaDetailComponent implements OnInit {
  alerta: IAlerta | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alerta }) => (this.alerta = alerta));
  }

  previousState(): void {
    window.history.back();
  }
}

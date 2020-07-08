import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAvisoMeteorologico } from 'app/shared/model/aviso-meteorologico.model';

@Component({
  selector: 'jhi-aviso-meteorologico-detail',
  templateUrl: './aviso-meteorologico-detail.component.html',
})
export class AvisoMeteorologicoDetailComponent implements OnInit {
  avisoMeteorologico: IAvisoMeteorologico | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avisoMeteorologico }) => (this.avisoMeteorologico = avisoMeteorologico));
  }

  previousState(): void {
    window.history.back();
  }
}

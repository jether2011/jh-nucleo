import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAcumuladoChuvaFaixa } from 'app/shared/model/acumulado-chuva-faixa.model';

@Component({
  selector: 'jhi-acumulado-chuva-faixa-detail',
  templateUrl: './acumulado-chuva-faixa-detail.component.html',
})
export class AcumuladoChuvaFaixaDetailComponent implements OnInit {
  acumuladoChuvaFaixa: IAcumuladoChuvaFaixa | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ acumuladoChuvaFaixa }) => (this.acumuladoChuvaFaixa = acumuladoChuvaFaixa));
  }

  previousState(): void {
    window.history.back();
  }
}

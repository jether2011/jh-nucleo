import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVariavelMeteorologica } from 'app/shared/model/variavel-meteorologica.model';

@Component({
  selector: 'jhi-variavel-meteorologica-detail',
  templateUrl: './variavel-meteorologica-detail.component.html',
})
export class VariavelMeteorologicaDetailComponent implements OnInit {
  variavelMeteorologica: IVariavelMeteorologica | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ variavelMeteorologica }) => (this.variavelMeteorologica = variavelMeteorologica));
  }

  previousState(): void {
    window.history.back();
  }
}

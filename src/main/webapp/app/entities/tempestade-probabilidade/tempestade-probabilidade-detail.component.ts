import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITempestadeProbabilidade } from 'app/shared/model/tempestade-probabilidade.model';

@Component({
  selector: 'jhi-tempestade-probabilidade-detail',
  templateUrl: './tempestade-probabilidade-detail.component.html',
})
export class TempestadeProbabilidadeDetailComponent implements OnInit {
  tempestadeProbabilidade: ITempestadeProbabilidade | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tempestadeProbabilidade }) => (this.tempestadeProbabilidade = tempestadeProbabilidade));
  }

  previousState(): void {
    window.history.back();
  }
}

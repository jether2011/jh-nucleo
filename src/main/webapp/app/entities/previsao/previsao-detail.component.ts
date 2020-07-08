import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrevisao } from 'app/shared/model/previsao.model';

@Component({
  selector: 'jhi-previsao-detail',
  templateUrl: './previsao-detail.component.html',
})
export class PrevisaoDetailComponent implements OnInit {
  previsao: IPrevisao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ previsao }) => (this.previsao = previsao));
  }

  previousState(): void {
    window.history.back();
  }
}

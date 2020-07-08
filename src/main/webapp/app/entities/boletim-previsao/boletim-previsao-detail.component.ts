import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBoletimPrevisao } from 'app/shared/model/boletim-previsao.model';

@Component({
  selector: 'jhi-boletim-previsao-detail',
  templateUrl: './boletim-previsao-detail.component.html',
})
export class BoletimPrevisaoDetailComponent implements OnInit {
  boletimPrevisao: IBoletimPrevisao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ boletimPrevisao }) => (this.boletimPrevisao = boletimPrevisao));
  }

  previousState(): void {
    window.history.back();
  }
}

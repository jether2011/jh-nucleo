import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBoletimPrevVariavelMet } from 'app/shared/model/boletim-prev-variavel-met.model';

@Component({
  selector: 'jhi-boletim-prev-variavel-met-detail',
  templateUrl: './boletim-prev-variavel-met-detail.component.html',
})
export class BoletimPrevVariavelMetDetailComponent implements OnInit {
  boletimPrevVariavelMet: IBoletimPrevVariavelMet | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ boletimPrevVariavelMet }) => (this.boletimPrevVariavelMet = boletimPrevVariavelMet));
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlanoRecurso } from 'app/shared/model/plano-recurso.model';

@Component({
  selector: 'jhi-plano-recurso-detail',
  templateUrl: './plano-recurso-detail.component.html',
})
export class PlanoRecursoDetailComponent implements OnInit {
  planoRecurso: IPlanoRecurso | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoRecurso }) => (this.planoRecurso = planoRecurso));
  }

  previousState(): void {
    window.history.back();
  }
}

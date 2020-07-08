import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecursoTipo } from 'app/shared/model/recurso-tipo.model';

@Component({
  selector: 'jhi-recurso-tipo-detail',
  templateUrl: './recurso-tipo-detail.component.html',
})
export class RecursoTipoDetailComponent implements OnInit {
  recursoTipo: IRecursoTipo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recursoTipo }) => (this.recursoTipo = recursoTipo));
  }

  previousState(): void {
    window.history.back();
  }
}

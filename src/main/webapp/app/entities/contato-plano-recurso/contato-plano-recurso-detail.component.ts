import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContatoPlanoRecurso } from 'app/shared/model/contato-plano-recurso.model';

@Component({
  selector: 'jhi-contato-plano-recurso-detail',
  templateUrl: './contato-plano-recurso-detail.component.html',
})
export class ContatoPlanoRecursoDetailComponent implements OnInit {
  contatoPlanoRecurso: IContatoPlanoRecurso | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contatoPlanoRecurso }) => (this.contatoPlanoRecurso = contatoPlanoRecurso));
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContatoAlvo } from 'app/shared/model/contato-alvo.model';

@Component({
  selector: 'jhi-contato-alvo-detail',
  templateUrl: './contato-alvo-detail.component.html',
})
export class ContatoAlvoDetailComponent implements OnInit {
  contatoAlvo: IContatoAlvo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contatoAlvo }) => (this.contatoAlvo = contatoAlvo));
  }

  previousState(): void {
    window.history.back();
  }
}

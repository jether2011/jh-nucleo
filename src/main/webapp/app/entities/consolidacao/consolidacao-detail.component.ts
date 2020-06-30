import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConsolidacao } from 'app/shared/model/consolidacao.model';

@Component({
  selector: 'jhi-consolidacao-detail',
  templateUrl: './consolidacao-detail.component.html',
})
export class ConsolidacaoDetailComponent implements OnInit {
  consolidacao: IConsolidacao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ consolidacao }) => (this.consolidacao = consolidacao));
  }

  previousState(): void {
    window.history.back();
  }
}

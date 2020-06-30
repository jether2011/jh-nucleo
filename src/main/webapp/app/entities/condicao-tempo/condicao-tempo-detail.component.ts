import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICondicaoTempo } from 'app/shared/model/condicao-tempo.model';

@Component({
  selector: 'jhi-condicao-tempo-detail',
  templateUrl: './condicao-tempo-detail.component.html',
})
export class CondicaoTempoDetailComponent implements OnInit {
  condicaoTempo: ICondicaoTempo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ condicaoTempo }) => (this.condicaoTempo = condicaoTempo));
  }

  previousState(): void {
    window.history.back();
  }
}

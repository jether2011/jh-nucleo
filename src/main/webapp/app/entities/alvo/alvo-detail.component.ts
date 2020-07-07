import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlvo } from 'app/shared/model/alvo.model';

@Component({
  selector: 'jhi-alvo-detail',
  templateUrl: './alvo-detail.component.html',
})
export class AlvoDetailComponent implements OnInit {
  alvo: IAlvo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alvo }) => (this.alvo = alvo));
  }

  previousState(): void {
    window.history.back();
  }
}

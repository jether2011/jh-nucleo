import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlvoBloqueio } from 'app/shared/model/alvo-bloqueio.model';

@Component({
  selector: 'jhi-alvo-bloqueio-detail',
  templateUrl: './alvo-bloqueio-detail.component.html',
})
export class AlvoBloqueioDetailComponent implements OnInit {
  alvoBloqueio: IAlvoBloqueio | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alvoBloqueio }) => (this.alvoBloqueio = alvoBloqueio));
  }

  previousState(): void {
    window.history.back();
  }
}

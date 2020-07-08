import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMeteograma } from 'app/shared/model/meteograma.model';

@Component({
  selector: 'jhi-meteograma-detail',
  templateUrl: './meteograma-detail.component.html',
})
export class MeteogramaDetailComponent implements OnInit {
  meteograma: IMeteograma | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ meteograma }) => (this.meteograma = meteograma));
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIntegrador } from 'app/shared/model/integrador.model';

@Component({
  selector: 'jhi-integrador-detail',
  templateUrl: './integrador-detail.component.html',
})
export class IntegradorDetailComponent implements OnInit {
  integrador: IIntegrador | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ integrador }) => (this.integrador = integrador));
  }

  previousState(): void {
    window.history.back();
  }
}

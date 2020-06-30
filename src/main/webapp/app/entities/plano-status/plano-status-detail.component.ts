import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlanoStatus } from 'app/shared/model/plano-status.model';

@Component({
  selector: 'jhi-plano-status-detail',
  templateUrl: './plano-status-detail.component.html',
})
export class PlanoStatusDetailComponent implements OnInit {
  planoStatus: IPlanoStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoStatus }) => (this.planoStatus = planoStatus));
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlanoLayer } from 'app/shared/model/plano-layer.model';

@Component({
  selector: 'jhi-plano-layer-detail',
  templateUrl: './plano-layer-detail.component.html',
})
export class PlanoLayerDetailComponent implements OnInit {
  planoLayer: IPlanoLayer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoLayer }) => (this.planoLayer = planoLayer));
  }

  previousState(): void {
    window.history.back();
  }
}

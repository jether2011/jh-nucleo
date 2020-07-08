import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILayer } from 'app/shared/model/layer.model';

@Component({
  selector: 'jhi-layer-detail',
  templateUrl: './layer-detail.component.html',
})
export class LayerDetailComponent implements OnInit {
  layer: ILayer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ layer }) => (this.layer = layer));
  }

  previousState(): void {
    window.history.back();
  }
}

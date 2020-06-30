import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDescarga } from 'app/shared/model/descarga.model';

@Component({
  selector: 'jhi-descarga-detail',
  templateUrl: './descarga-detail.component.html',
})
export class DescargaDetailComponent implements OnInit {
  descarga: IDescarga | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ descarga }) => (this.descarga = descarga));
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDescargaUnidade } from 'app/shared/model/descarga-unidade.model';

@Component({
  selector: 'jhi-descarga-unidade-detail',
  templateUrl: './descarga-unidade-detail.component.html',
})
export class DescargaUnidadeDetailComponent implements OnInit {
  descargaUnidade: IDescargaUnidade | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ descargaUnidade }) => (this.descargaUnidade = descargaUnidade));
  }

  previousState(): void {
    window.history.back();
  }
}

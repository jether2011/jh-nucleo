import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDescargaTipo } from 'app/shared/model/descarga-tipo.model';

@Component({
  selector: 'jhi-descarga-tipo-detail',
  templateUrl: './descarga-tipo-detail.component.html',
})
export class DescargaTipoDetailComponent implements OnInit {
  descargaTipo: IDescargaTipo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ descargaTipo }) => (this.descargaTipo = descargaTipo));
  }

  previousState(): void {
    window.history.back();
  }
}

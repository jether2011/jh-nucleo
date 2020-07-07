import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAvisoTipo } from 'app/shared/model/aviso-tipo.model';

@Component({
  selector: 'jhi-aviso-tipo-detail',
  templateUrl: './aviso-tipo-detail.component.html',
})
export class AvisoTipoDetailComponent implements OnInit {
  avisoTipo: IAvisoTipo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avisoTipo }) => (this.avisoTipo = avisoTipo));
  }

  previousState(): void {
    window.history.back();
  }
}

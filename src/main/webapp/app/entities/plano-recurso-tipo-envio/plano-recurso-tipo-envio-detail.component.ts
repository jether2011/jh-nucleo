import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlanoRecursoTipoEnvio } from 'app/shared/model/plano-recurso-tipo-envio.model';

@Component({
  selector: 'jhi-plano-recurso-tipo-envio-detail',
  templateUrl: './plano-recurso-tipo-envio-detail.component.html',
})
export class PlanoRecursoTipoEnvioDetailComponent implements OnInit {
  planoRecursoTipoEnvio: IPlanoRecursoTipoEnvio | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoRecursoTipoEnvio }) => (this.planoRecursoTipoEnvio = planoRecursoTipoEnvio));
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoEnvioIntegrador } from 'app/shared/model/tipo-envio-integrador.model';

@Component({
  selector: 'jhi-tipo-envio-integrador-detail',
  templateUrl: './tipo-envio-integrador-detail.component.html',
})
export class TipoEnvioIntegradorDetailComponent implements OnInit {
  tipoEnvioIntegrador: ITipoEnvioIntegrador | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoEnvioIntegrador }) => (this.tipoEnvioIntegrador = tipoEnvioIntegrador));
  }

  previousState(): void {
    window.history.back();
  }
}

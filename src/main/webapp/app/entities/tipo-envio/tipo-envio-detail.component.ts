import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoEnvio } from 'app/shared/model/tipo-envio.model';

@Component({
  selector: 'jhi-tipo-envio-detail',
  templateUrl: './tipo-envio-detail.component.html',
})
export class TipoEnvioDetailComponent implements OnInit {
  tipoEnvio: ITipoEnvio | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoEnvio }) => (this.tipoEnvio = tipoEnvio));
  }

  previousState(): void {
    window.history.back();
  }
}

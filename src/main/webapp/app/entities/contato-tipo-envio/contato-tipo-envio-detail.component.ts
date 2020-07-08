import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContatoTipoEnvio } from 'app/shared/model/contato-tipo-envio.model';

@Component({
  selector: 'jhi-contato-tipo-envio-detail',
  templateUrl: './contato-tipo-envio-detail.component.html',
})
export class ContatoTipoEnvioDetailComponent implements OnInit {
  contatoTipoEnvio: IContatoTipoEnvio | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contatoTipoEnvio }) => (this.contatoTipoEnvio = contatoTipoEnvio));
  }

  previousState(): void {
    window.history.back();
  }
}

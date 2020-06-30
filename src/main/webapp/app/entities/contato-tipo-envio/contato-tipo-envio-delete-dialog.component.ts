import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContatoTipoEnvio } from 'app/shared/model/contato-tipo-envio.model';
import { ContatoTipoEnvioService } from './contato-tipo-envio.service';

@Component({
  templateUrl: './contato-tipo-envio-delete-dialog.component.html',
})
export class ContatoTipoEnvioDeleteDialogComponent {
  contatoTipoEnvio?: IContatoTipoEnvio;

  constructor(
    protected contatoTipoEnvioService: ContatoTipoEnvioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contatoTipoEnvioService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contatoTipoEnvioListModification');
      this.activeModal.close();
    });
  }
}

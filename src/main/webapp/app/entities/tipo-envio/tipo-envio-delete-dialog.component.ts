import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoEnvio } from 'app/shared/model/tipo-envio.model';
import { TipoEnvioService } from './tipo-envio.service';

@Component({
  templateUrl: './tipo-envio-delete-dialog.component.html',
})
export class TipoEnvioDeleteDialogComponent {
  tipoEnvio?: ITipoEnvio;

  constructor(protected tipoEnvioService: TipoEnvioService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoEnvioService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoEnvioListModification');
      this.activeModal.close();
    });
  }
}

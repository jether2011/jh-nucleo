import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoEnvioIntegrador } from 'app/shared/model/tipo-envio-integrador.model';
import { TipoEnvioIntegradorService } from './tipo-envio-integrador.service';

@Component({
  templateUrl: './tipo-envio-integrador-delete-dialog.component.html',
})
export class TipoEnvioIntegradorDeleteDialogComponent {
  tipoEnvioIntegrador?: ITipoEnvioIntegrador;

  constructor(
    protected tipoEnvioIntegradorService: TipoEnvioIntegradorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoEnvioIntegradorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoEnvioIntegradorListModification');
      this.activeModal.close();
    });
  }
}

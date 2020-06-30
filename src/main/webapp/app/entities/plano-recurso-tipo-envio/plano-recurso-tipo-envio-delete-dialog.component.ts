import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanoRecursoTipoEnvio } from 'app/shared/model/plano-recurso-tipo-envio.model';
import { PlanoRecursoTipoEnvioService } from './plano-recurso-tipo-envio.service';

@Component({
  templateUrl: './plano-recurso-tipo-envio-delete-dialog.component.html',
})
export class PlanoRecursoTipoEnvioDeleteDialogComponent {
  planoRecursoTipoEnvio?: IPlanoRecursoTipoEnvio;

  constructor(
    protected planoRecursoTipoEnvioService: PlanoRecursoTipoEnvioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoRecursoTipoEnvioService.delete(id).subscribe(() => {
      this.eventManager.broadcast('planoRecursoTipoEnvioListModification');
      this.activeModal.close();
    });
  }
}

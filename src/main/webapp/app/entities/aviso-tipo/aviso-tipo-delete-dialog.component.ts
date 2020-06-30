import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAvisoTipo } from 'app/shared/model/aviso-tipo.model';
import { AvisoTipoService } from './aviso-tipo.service';

@Component({
  templateUrl: './aviso-tipo-delete-dialog.component.html',
})
export class AvisoTipoDeleteDialogComponent {
  avisoTipo?: IAvisoTipo;

  constructor(protected avisoTipoService: AvisoTipoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avisoTipoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('avisoTipoListModification');
      this.activeModal.close();
    });
  }
}

import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAviso } from 'app/shared/model/aviso.model';
import { AvisoService } from './aviso.service';

@Component({
  templateUrl: './aviso-delete-dialog.component.html',
})
export class AvisoDeleteDialogComponent {
  aviso?: IAviso;

  constructor(protected avisoService: AvisoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avisoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('avisoListModification');
      this.activeModal.close();
    });
  }
}

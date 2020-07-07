import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVoceSabia } from 'app/shared/model/voce-sabia.model';
import { VoceSabiaService } from './voce-sabia.service';

@Component({
  templateUrl: './voce-sabia-delete-dialog.component.html',
})
export class VoceSabiaDeleteDialogComponent {
  voceSabia?: IVoceSabia;

  constructor(protected voceSabiaService: VoceSabiaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.voceSabiaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('voceSabiaListModification');
      this.activeModal.close();
    });
  }
}

import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBoletim } from 'app/shared/model/boletim.model';
import { BoletimService } from './boletim.service';

@Component({
  templateUrl: './boletim-delete-dialog.component.html',
})
export class BoletimDeleteDialogComponent {
  boletim?: IBoletim;

  constructor(protected boletimService: BoletimService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.boletimService.delete(id).subscribe(() => {
      this.eventManager.broadcast('boletimListModification');
      this.activeModal.close();
    });
  }
}

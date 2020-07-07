import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlano } from 'app/shared/model/plano.model';
import { PlanoService } from './plano.service';

@Component({
  templateUrl: './plano-delete-dialog.component.html',
})
export class PlanoDeleteDialogComponent {
  plano?: IPlano;

  constructor(protected planoService: PlanoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('planoListModification');
      this.activeModal.close();
    });
  }
}

import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanoStatus } from 'app/shared/model/plano-status.model';
import { PlanoStatusService } from './plano-status.service';

@Component({
  templateUrl: './plano-status-delete-dialog.component.html',
})
export class PlanoStatusDeleteDialogComponent {
  planoStatus?: IPlanoStatus;

  constructor(
    protected planoStatusService: PlanoStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoStatusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('planoStatusListModification');
      this.activeModal.close();
    });
  }
}

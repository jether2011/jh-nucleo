import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRede } from 'app/shared/model/rede.model';
import { RedeService } from './rede.service';

@Component({
  templateUrl: './rede-delete-dialog.component.html',
})
export class RedeDeleteDialogComponent {
  rede?: IRede;

  constructor(protected redeService: RedeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.redeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('redeListModification');
      this.activeModal.close();
    });
  }
}

import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanoRede } from 'app/shared/model/plano-rede.model';
import { PlanoRedeService } from './plano-rede.service';

@Component({
  templateUrl: './plano-rede-delete-dialog.component.html',
})
export class PlanoRedeDeleteDialogComponent {
  planoRede?: IPlanoRede;

  constructor(protected planoRedeService: PlanoRedeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoRedeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('planoRedeListModification');
      this.activeModal.close();
    });
  }
}

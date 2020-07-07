import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlvo } from 'app/shared/model/alvo.model';
import { AlvoService } from './alvo.service';

@Component({
  templateUrl: './alvo-delete-dialog.component.html',
})
export class AlvoDeleteDialogComponent {
  alvo?: IAlvo;

  constructor(protected alvoService: AlvoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alvoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('alvoListModification');
      this.activeModal.close();
    });
  }
}

import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlvoBloqueio } from 'app/shared/model/alvo-bloqueio.model';
import { AlvoBloqueioService } from './alvo-bloqueio.service';

@Component({
  templateUrl: './alvo-bloqueio-delete-dialog.component.html',
})
export class AlvoBloqueioDeleteDialogComponent {
  alvoBloqueio?: IAlvoBloqueio;

  constructor(
    protected alvoBloqueioService: AlvoBloqueioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alvoBloqueioService.delete(id).subscribe(() => {
      this.eventManager.broadcast('alvoBloqueioListModification');
      this.activeModal.close();
    });
  }
}

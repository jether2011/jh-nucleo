import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIntegrador } from 'app/shared/model/integrador.model';
import { IntegradorService } from './integrador.service';

@Component({
  templateUrl: './integrador-delete-dialog.component.html',
})
export class IntegradorDeleteDialogComponent {
  integrador?: IIntegrador;

  constructor(
    protected integradorService: IntegradorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.integradorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('integradorListModification');
      this.activeModal.close();
    });
  }
}

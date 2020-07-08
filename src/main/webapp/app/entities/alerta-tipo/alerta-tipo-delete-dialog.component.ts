import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlertaTipo } from 'app/shared/model/alerta-tipo.model';
import { AlertaTipoService } from './alerta-tipo.service';

@Component({
  templateUrl: './alerta-tipo-delete-dialog.component.html',
})
export class AlertaTipoDeleteDialogComponent {
  alertaTipo?: IAlertaTipo;

  constructor(
    protected alertaTipoService: AlertaTipoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alertaTipoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('alertaTipoListModification');
      this.activeModal.close();
    });
  }
}

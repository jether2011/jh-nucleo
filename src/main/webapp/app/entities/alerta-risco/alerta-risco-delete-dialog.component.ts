import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlertaRisco } from 'app/shared/model/alerta-risco.model';
import { AlertaRiscoService } from './alerta-risco.service';

@Component({
  templateUrl: './alerta-risco-delete-dialog.component.html',
})
export class AlertaRiscoDeleteDialogComponent {
  alertaRisco?: IAlertaRisco;

  constructor(
    protected alertaRiscoService: AlertaRiscoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alertaRiscoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('alertaRiscoListModification');
      this.activeModal.close();
    });
  }
}

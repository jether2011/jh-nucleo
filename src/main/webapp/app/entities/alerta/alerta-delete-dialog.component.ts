import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlerta } from 'app/shared/model/alerta.model';
import { AlertaService } from './alerta.service';

@Component({
  templateUrl: './alerta-delete-dialog.component.html',
})
export class AlertaDeleteDialogComponent {
  alerta?: IAlerta;

  constructor(protected alertaService: AlertaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alertaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('alertaListModification');
      this.activeModal.close();
    });
  }
}

import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlertaFerramenta } from 'app/shared/model/alerta-ferramenta.model';
import { AlertaFerramentaService } from './alerta-ferramenta.service';

@Component({
  templateUrl: './alerta-ferramenta-delete-dialog.component.html',
})
export class AlertaFerramentaDeleteDialogComponent {
  alertaFerramenta?: IAlertaFerramenta;

  constructor(
    protected alertaFerramentaService: AlertaFerramentaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alertaFerramentaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('alertaFerramentaListModification');
      this.activeModal.close();
    });
  }
}

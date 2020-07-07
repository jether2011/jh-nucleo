import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAvisoMeteorologico } from 'app/shared/model/aviso-meteorologico.model';
import { AvisoMeteorologicoService } from './aviso-meteorologico.service';

@Component({
  templateUrl: './aviso-meteorologico-delete-dialog.component.html',
})
export class AvisoMeteorologicoDeleteDialogComponent {
  avisoMeteorologico?: IAvisoMeteorologico;

  constructor(
    protected avisoMeteorologicoService: AvisoMeteorologicoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avisoMeteorologicoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('avisoMeteorologicoListModification');
      this.activeModal.close();
    });
  }
}

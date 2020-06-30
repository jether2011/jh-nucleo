import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDescarga } from 'app/shared/model/descarga.model';
import { DescargaService } from './descarga.service';

@Component({
  templateUrl: './descarga-delete-dialog.component.html',
})
export class DescargaDeleteDialogComponent {
  descarga?: IDescarga;

  constructor(protected descargaService: DescargaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.descargaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('descargaListModification');
      this.activeModal.close();
    });
  }
}

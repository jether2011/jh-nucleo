import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDescargaTipo } from 'app/shared/model/descarga-tipo.model';
import { DescargaTipoService } from './descarga-tipo.service';

@Component({
  templateUrl: './descarga-tipo-delete-dialog.component.html',
})
export class DescargaTipoDeleteDialogComponent {
  descargaTipo?: IDescargaTipo;

  constructor(
    protected descargaTipoService: DescargaTipoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.descargaTipoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('descargaTipoListModification');
      this.activeModal.close();
    });
  }
}

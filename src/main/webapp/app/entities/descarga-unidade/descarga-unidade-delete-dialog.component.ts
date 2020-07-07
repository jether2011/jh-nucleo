import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDescargaUnidade } from 'app/shared/model/descarga-unidade.model';
import { DescargaUnidadeService } from './descarga-unidade.service';

@Component({
  templateUrl: './descarga-unidade-delete-dialog.component.html',
})
export class DescargaUnidadeDeleteDialogComponent {
  descargaUnidade?: IDescargaUnidade;

  constructor(
    protected descargaUnidadeService: DescargaUnidadeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.descargaUnidadeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('descargaUnidadeListModification');
      this.activeModal.close();
    });
  }
}

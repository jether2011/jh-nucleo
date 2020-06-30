import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInformativo } from 'app/shared/model/informativo.model';
import { InformativoService } from './informativo.service';

@Component({
  templateUrl: './informativo-delete-dialog.component.html',
})
export class InformativoDeleteDialogComponent {
  informativo?: IInformativo;

  constructor(
    protected informativoService: InformativoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.informativoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('informativoListModification');
      this.activeModal.close();
    });
  }
}

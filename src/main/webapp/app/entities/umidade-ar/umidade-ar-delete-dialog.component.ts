import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUmidadeAr } from 'app/shared/model/umidade-ar.model';
import { UmidadeArService } from './umidade-ar.service';

@Component({
  templateUrl: './umidade-ar-delete-dialog.component.html',
})
export class UmidadeArDeleteDialogComponent {
  umidadeAr?: IUmidadeAr;

  constructor(protected umidadeArService: UmidadeArService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.umidadeArService.delete(id).subscribe(() => {
      this.eventManager.broadcast('umidadeArListModification');
      this.activeModal.close();
    });
  }
}

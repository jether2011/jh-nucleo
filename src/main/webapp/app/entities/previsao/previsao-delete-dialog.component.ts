import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrevisao } from 'app/shared/model/previsao.model';
import { PrevisaoService } from './previsao.service';

@Component({
  templateUrl: './previsao-delete-dialog.component.html',
})
export class PrevisaoDeleteDialogComponent {
  previsao?: IPrevisao;

  constructor(protected previsaoService: PrevisaoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.previsaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('previsaoListModification');
      this.activeModal.close();
    });
  }
}

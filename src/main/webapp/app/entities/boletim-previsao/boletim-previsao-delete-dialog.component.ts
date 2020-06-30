import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBoletimPrevisao } from 'app/shared/model/boletim-previsao.model';
import { BoletimPrevisaoService } from './boletim-previsao.service';

@Component({
  templateUrl: './boletim-previsao-delete-dialog.component.html',
})
export class BoletimPrevisaoDeleteDialogComponent {
  boletimPrevisao?: IBoletimPrevisao;

  constructor(
    protected boletimPrevisaoService: BoletimPrevisaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.boletimPrevisaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('boletimPrevisaoListModification');
      this.activeModal.close();
    });
  }
}

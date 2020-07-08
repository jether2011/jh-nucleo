import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITempestadeProbabilidade } from 'app/shared/model/tempestade-probabilidade.model';
import { TempestadeProbabilidadeService } from './tempestade-probabilidade.service';

@Component({
  templateUrl: './tempestade-probabilidade-delete-dialog.component.html',
})
export class TempestadeProbabilidadeDeleteDialogComponent {
  tempestadeProbabilidade?: ITempestadeProbabilidade;

  constructor(
    protected tempestadeProbabilidadeService: TempestadeProbabilidadeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tempestadeProbabilidadeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tempestadeProbabilidadeListModification');
      this.activeModal.close();
    });
  }
}

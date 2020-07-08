import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConsolidacao } from 'app/shared/model/consolidacao.model';
import { ConsolidacaoService } from './consolidacao.service';

@Component({
  templateUrl: './consolidacao-delete-dialog.component.html',
})
export class ConsolidacaoDeleteDialogComponent {
  consolidacao?: IConsolidacao;

  constructor(
    protected consolidacaoService: ConsolidacaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.consolidacaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('consolidacaoListModification');
      this.activeModal.close();
    });
  }
}

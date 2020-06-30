import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICondicaoTempo } from 'app/shared/model/condicao-tempo.model';
import { CondicaoTempoService } from './condicao-tempo.service';

@Component({
  templateUrl: './condicao-tempo-delete-dialog.component.html',
})
export class CondicaoTempoDeleteDialogComponent {
  condicaoTempo?: ICondicaoTempo;

  constructor(
    protected condicaoTempoService: CondicaoTempoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.condicaoTempoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('condicaoTempoListModification');
      this.activeModal.close();
    });
  }
}

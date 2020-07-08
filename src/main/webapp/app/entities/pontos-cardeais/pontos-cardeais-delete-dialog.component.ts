import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPontosCardeais } from 'app/shared/model/pontos-cardeais.model';
import { PontosCardeaisService } from './pontos-cardeais.service';

@Component({
  templateUrl: './pontos-cardeais-delete-dialog.component.html',
})
export class PontosCardeaisDeleteDialogComponent {
  pontosCardeais?: IPontosCardeais;

  constructor(
    protected pontosCardeaisService: PontosCardeaisService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pontosCardeaisService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pontosCardeaisListModification');
      this.activeModal.close();
    });
  }
}

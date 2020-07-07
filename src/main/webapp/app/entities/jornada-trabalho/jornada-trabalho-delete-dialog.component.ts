import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJornadaTrabalho } from 'app/shared/model/jornada-trabalho.model';
import { JornadaTrabalhoService } from './jornada-trabalho.service';

@Component({
  templateUrl: './jornada-trabalho-delete-dialog.component.html',
})
export class JornadaTrabalhoDeleteDialogComponent {
  jornadaTrabalho?: IJornadaTrabalho;

  constructor(
    protected jornadaTrabalhoService: JornadaTrabalhoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jornadaTrabalhoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('jornadaTrabalhoListModification');
      this.activeModal.close();
    });
  }
}

import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDiaSemana } from 'app/shared/model/dia-semana.model';
import { DiaSemanaService } from './dia-semana.service';

@Component({
  templateUrl: './dia-semana-delete-dialog.component.html',
})
export class DiaSemanaDeleteDialogComponent {
  diaSemana?: IDiaSemana;

  constructor(protected diaSemanaService: DiaSemanaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.diaSemanaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('diaSemanaListModification');
      this.activeModal.close();
    });
  }
}

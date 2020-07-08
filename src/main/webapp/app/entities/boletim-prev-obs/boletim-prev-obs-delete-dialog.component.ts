import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBoletimPrevObs } from 'app/shared/model/boletim-prev-obs.model';
import { BoletimPrevObsService } from './boletim-prev-obs.service';

@Component({
  templateUrl: './boletim-prev-obs-delete-dialog.component.html',
})
export class BoletimPrevObsDeleteDialogComponent {
  boletimPrevObs?: IBoletimPrevObs;

  constructor(
    protected boletimPrevObsService: BoletimPrevObsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.boletimPrevObsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('boletimPrevObsListModification');
      this.activeModal.close();
    });
  }
}

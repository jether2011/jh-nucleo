import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBoletimPrevVariavelMet } from 'app/shared/model/boletim-prev-variavel-met.model';
import { BoletimPrevVariavelMetService } from './boletim-prev-variavel-met.service';

@Component({
  templateUrl: './boletim-prev-variavel-met-delete-dialog.component.html',
})
export class BoletimPrevVariavelMetDeleteDialogComponent {
  boletimPrevVariavelMet?: IBoletimPrevVariavelMet;

  constructor(
    protected boletimPrevVariavelMetService: BoletimPrevVariavelMetService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.boletimPrevVariavelMetService.delete(id).subscribe(() => {
      this.eventManager.broadcast('boletimPrevVariavelMetListModification');
      this.activeModal.close();
    });
  }
}

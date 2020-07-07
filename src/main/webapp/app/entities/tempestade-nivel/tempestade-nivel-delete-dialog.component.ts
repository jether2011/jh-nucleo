import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITempestadeNivel } from 'app/shared/model/tempestade-nivel.model';
import { TempestadeNivelService } from './tempestade-nivel.service';

@Component({
  templateUrl: './tempestade-nivel-delete-dialog.component.html',
})
export class TempestadeNivelDeleteDialogComponent {
  tempestadeNivel?: ITempestadeNivel;

  constructor(
    protected tempestadeNivelService: TempestadeNivelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tempestadeNivelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tempestadeNivelListModification');
      this.activeModal.close();
    });
  }
}

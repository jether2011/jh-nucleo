import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMeteograma } from 'app/shared/model/meteograma.model';
import { MeteogramaService } from './meteograma.service';

@Component({
  templateUrl: './meteograma-delete-dialog.component.html',
})
export class MeteogramaDeleteDialogComponent {
  meteograma?: IMeteograma;

  constructor(
    protected meteogramaService: MeteogramaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.meteogramaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('meteogramaListModification');
      this.activeModal.close();
    });
  }
}

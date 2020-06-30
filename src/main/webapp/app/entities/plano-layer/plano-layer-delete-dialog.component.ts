import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanoLayer } from 'app/shared/model/plano-layer.model';
import { PlanoLayerService } from './plano-layer.service';

@Component({
  templateUrl: './plano-layer-delete-dialog.component.html',
})
export class PlanoLayerDeleteDialogComponent {
  planoLayer?: IPlanoLayer;

  constructor(
    protected planoLayerService: PlanoLayerService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoLayerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('planoLayerListModification');
      this.activeModal.close();
    });
  }
}

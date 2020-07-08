import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILayer } from 'app/shared/model/layer.model';
import { LayerService } from './layer.service';

@Component({
  templateUrl: './layer-delete-dialog.component.html',
})
export class LayerDeleteDialogComponent {
  layer?: ILayer;

  constructor(protected layerService: LayerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.layerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('layerListModification');
      this.activeModal.close();
    });
  }
}

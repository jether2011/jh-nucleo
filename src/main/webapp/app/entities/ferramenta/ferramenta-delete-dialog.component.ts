import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFerramenta } from 'app/shared/model/ferramenta.model';
import { FerramentaService } from './ferramenta.service';

@Component({
  templateUrl: './ferramenta-delete-dialog.component.html',
})
export class FerramentaDeleteDialogComponent {
  ferramenta?: IFerramenta;

  constructor(
    protected ferramentaService: FerramentaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ferramentaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ferramentaListModification');
      this.activeModal.close();
    });
  }
}

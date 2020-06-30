import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoFerramenta } from 'app/shared/model/tipo-ferramenta.model';
import { TipoFerramentaService } from './tipo-ferramenta.service';

@Component({
  templateUrl: './tipo-ferramenta-delete-dialog.component.html',
})
export class TipoFerramentaDeleteDialogComponent {
  tipoFerramenta?: ITipoFerramenta;

  constructor(
    protected tipoFerramentaService: TipoFerramentaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoFerramentaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoFerramentaListModification');
      this.activeModal.close();
    });
  }
}

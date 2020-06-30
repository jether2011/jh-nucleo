import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecursoTipo } from 'app/shared/model/recurso-tipo.model';
import { RecursoTipoService } from './recurso-tipo.service';

@Component({
  templateUrl: './recurso-tipo-delete-dialog.component.html',
})
export class RecursoTipoDeleteDialogComponent {
  recursoTipo?: IRecursoTipo;

  constructor(
    protected recursoTipoService: RecursoTipoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.recursoTipoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('recursoTipoListModification');
      this.activeModal.close();
    });
  }
}

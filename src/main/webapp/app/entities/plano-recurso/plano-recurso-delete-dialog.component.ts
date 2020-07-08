import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanoRecurso } from 'app/shared/model/plano-recurso.model';
import { PlanoRecursoService } from './plano-recurso.service';

@Component({
  templateUrl: './plano-recurso-delete-dialog.component.html',
})
export class PlanoRecursoDeleteDialogComponent {
  planoRecurso?: IPlanoRecurso;

  constructor(
    protected planoRecursoService: PlanoRecursoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoRecursoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('planoRecursoListModification');
      this.activeModal.close();
    });
  }
}

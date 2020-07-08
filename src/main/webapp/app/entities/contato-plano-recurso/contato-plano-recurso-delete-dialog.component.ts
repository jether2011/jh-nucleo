import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContatoPlanoRecurso } from 'app/shared/model/contato-plano-recurso.model';
import { ContatoPlanoRecursoService } from './contato-plano-recurso.service';

@Component({
  templateUrl: './contato-plano-recurso-delete-dialog.component.html',
})
export class ContatoPlanoRecursoDeleteDialogComponent {
  contatoPlanoRecurso?: IContatoPlanoRecurso;

  constructor(
    protected contatoPlanoRecursoService: ContatoPlanoRecursoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contatoPlanoRecursoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contatoPlanoRecursoListModification');
      this.activeModal.close();
    });
  }
}

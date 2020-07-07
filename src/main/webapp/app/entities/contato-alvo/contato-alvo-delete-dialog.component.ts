import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContatoAlvo } from 'app/shared/model/contato-alvo.model';
import { ContatoAlvoService } from './contato-alvo.service';

@Component({
  templateUrl: './contato-alvo-delete-dialog.component.html',
})
export class ContatoAlvoDeleteDialogComponent {
  contatoAlvo?: IContatoAlvo;

  constructor(
    protected contatoAlvoService: ContatoAlvoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contatoAlvoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contatoAlvoListModification');
      this.activeModal.close();
    });
  }
}

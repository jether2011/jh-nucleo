import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPerfil } from 'app/shared/model/perfil.model';
import { PerfilService } from './perfil.service';

@Component({
  templateUrl: './perfil-delete-dialog.component.html',
})
export class PerfilDeleteDialogComponent {
  perfil?: IPerfil;

  constructor(protected perfilService: PerfilService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.perfilService.delete(id).subscribe(() => {
      this.eventManager.broadcast('perfilListModification');
      this.activeModal.close();
    });
  }
}

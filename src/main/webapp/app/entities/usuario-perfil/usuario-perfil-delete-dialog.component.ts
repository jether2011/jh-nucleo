import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUsuarioPerfil } from 'app/shared/model/usuario-perfil.model';
import { UsuarioPerfilService } from './usuario-perfil.service';

@Component({
  templateUrl: './usuario-perfil-delete-dialog.component.html',
})
export class UsuarioPerfilDeleteDialogComponent {
  usuarioPerfil?: IUsuarioPerfil;

  constructor(
    protected usuarioPerfilService: UsuarioPerfilService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.usuarioPerfilService.delete(id).subscribe(() => {
      this.eventManager.broadcast('usuarioPerfilListModification');
      this.activeModal.close();
    });
  }
}

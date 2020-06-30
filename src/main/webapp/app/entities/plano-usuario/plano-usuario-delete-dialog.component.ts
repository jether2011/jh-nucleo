import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanoUsuario } from 'app/shared/model/plano-usuario.model';
import { PlanoUsuarioService } from './plano-usuario.service';

@Component({
  templateUrl: './plano-usuario-delete-dialog.component.html',
})
export class PlanoUsuarioDeleteDialogComponent {
  planoUsuario?: IPlanoUsuario;

  constructor(
    protected planoUsuarioService: PlanoUsuarioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoUsuarioService.delete(id).subscribe(() => {
      this.eventManager.broadcast('planoUsuarioListModification');
      this.activeModal.close();
    });
  }
}

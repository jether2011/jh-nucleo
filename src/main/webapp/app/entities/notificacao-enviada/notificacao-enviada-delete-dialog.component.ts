import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INotificacaoEnviada } from 'app/shared/model/notificacao-enviada.model';
import { NotificacaoEnviadaService } from './notificacao-enviada.service';

@Component({
  templateUrl: './notificacao-enviada-delete-dialog.component.html',
})
export class NotificacaoEnviadaDeleteDialogComponent {
  notificacaoEnviada?: INotificacaoEnviada;

  constructor(
    protected notificacaoEnviadaService: NotificacaoEnviadaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.notificacaoEnviadaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('notificacaoEnviadaListModification');
      this.activeModal.close();
    });
  }
}

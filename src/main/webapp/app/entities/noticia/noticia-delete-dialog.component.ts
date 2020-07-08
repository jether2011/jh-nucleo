import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INoticia } from 'app/shared/model/noticia.model';
import { NoticiaService } from './noticia.service';

@Component({
  templateUrl: './noticia-delete-dialog.component.html',
})
export class NoticiaDeleteDialogComponent {
  noticia?: INoticia;

  constructor(protected noticiaService: NoticiaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.noticiaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('noticiaListModification');
      this.activeModal.close();
    });
  }
}

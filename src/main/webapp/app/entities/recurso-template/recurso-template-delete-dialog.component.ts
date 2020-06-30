import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecursoTemplate } from 'app/shared/model/recurso-template.model';
import { RecursoTemplateService } from './recurso-template.service';

@Component({
  templateUrl: './recurso-template-delete-dialog.component.html',
})
export class RecursoTemplateDeleteDialogComponent {
  recursoTemplate?: IRecursoTemplate;

  constructor(
    protected recursoTemplateService: RecursoTemplateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.recursoTemplateService.delete(id).subscribe(() => {
      this.eventManager.broadcast('recursoTemplateListModification');
      this.activeModal.close();
    });
  }
}

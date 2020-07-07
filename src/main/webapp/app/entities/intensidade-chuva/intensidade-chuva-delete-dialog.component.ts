import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIntensidadeChuva } from 'app/shared/model/intensidade-chuva.model';
import { IntensidadeChuvaService } from './intensidade-chuva.service';

@Component({
  templateUrl: './intensidade-chuva-delete-dialog.component.html',
})
export class IntensidadeChuvaDeleteDialogComponent {
  intensidadeChuva?: IIntensidadeChuva;

  constructor(
    protected intensidadeChuvaService: IntensidadeChuvaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.intensidadeChuvaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('intensidadeChuvaListModification');
      this.activeModal.close();
    });
  }
}

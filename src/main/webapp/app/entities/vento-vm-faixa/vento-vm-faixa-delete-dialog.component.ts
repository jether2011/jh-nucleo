import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVentoVmFaixa } from 'app/shared/model/vento-vm-faixa.model';
import { VentoVmFaixaService } from './vento-vm-faixa.service';

@Component({
  templateUrl: './vento-vm-faixa-delete-dialog.component.html',
})
export class VentoVmFaixaDeleteDialogComponent {
  ventoVmFaixa?: IVentoVmFaixa;

  constructor(
    protected ventoVmFaixaService: VentoVmFaixaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ventoVmFaixaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ventoVmFaixaListModification');
      this.activeModal.close();
    });
  }
}

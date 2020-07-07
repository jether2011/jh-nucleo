import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAcumuladoChuvaFaixa } from 'app/shared/model/acumulado-chuva-faixa.model';
import { AcumuladoChuvaFaixaService } from './acumulado-chuva-faixa.service';

@Component({
  templateUrl: './acumulado-chuva-faixa-delete-dialog.component.html',
})
export class AcumuladoChuvaFaixaDeleteDialogComponent {
  acumuladoChuvaFaixa?: IAcumuladoChuvaFaixa;

  constructor(
    protected acumuladoChuvaFaixaService: AcumuladoChuvaFaixaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.acumuladoChuvaFaixaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('acumuladoChuvaFaixaListModification');
      this.activeModal.close();
    });
  }
}

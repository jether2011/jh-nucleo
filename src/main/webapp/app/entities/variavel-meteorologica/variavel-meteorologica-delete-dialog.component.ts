import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVariavelMeteorologica } from 'app/shared/model/variavel-meteorologica.model';
import { VariavelMeteorologicaService } from './variavel-meteorologica.service';

@Component({
  templateUrl: './variavel-meteorologica-delete-dialog.component.html',
})
export class VariavelMeteorologicaDeleteDialogComponent {
  variavelMeteorologica?: IVariavelMeteorologica;

  constructor(
    protected variavelMeteorologicaService: VariavelMeteorologicaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.variavelMeteorologicaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('variavelMeteorologicaListModification');
      this.activeModal.close();
    });
  }
}

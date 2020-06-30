import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVentoVmFaixa, VentoVmFaixa } from 'app/shared/model/vento-vm-faixa.model';
import { VentoVmFaixaService } from './vento-vm-faixa.service';

@Component({
  selector: 'jhi-vento-vm-faixa-update',
  templateUrl: './vento-vm-faixa-update.component.html',
})
export class VentoVmFaixaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    de: [],
    ate: [],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected ventoVmFaixaService: VentoVmFaixaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ventoVmFaixa }) => {
      if (!ventoVmFaixa.id) {
        const today = moment().startOf('day');
        ventoVmFaixa.created = today;
        ventoVmFaixa.updated = today;
      }

      this.updateForm(ventoVmFaixa);
    });
  }

  updateForm(ventoVmFaixa: IVentoVmFaixa): void {
    this.editForm.patchValue({
      id: ventoVmFaixa.id,
      name: ventoVmFaixa.name,
      descricao: ventoVmFaixa.descricao,
      de: ventoVmFaixa.de,
      ate: ventoVmFaixa.ate,
      created: ventoVmFaixa.created ? ventoVmFaixa.created.format(DATE_TIME_FORMAT) : null,
      updated: ventoVmFaixa.updated ? ventoVmFaixa.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ventoVmFaixa = this.createFromForm();
    if (ventoVmFaixa.id !== undefined) {
      this.subscribeToSaveResponse(this.ventoVmFaixaService.update(ventoVmFaixa));
    } else {
      this.subscribeToSaveResponse(this.ventoVmFaixaService.create(ventoVmFaixa));
    }
  }

  private createFromForm(): IVentoVmFaixa {
    return {
      ...new VentoVmFaixa(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      de: this.editForm.get(['de'])!.value,
      ate: this.editForm.get(['ate'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVentoVmFaixa>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}

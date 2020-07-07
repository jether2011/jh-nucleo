import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVariavelMeteorologica, VariavelMeteorologica } from 'app/shared/model/variavel-meteorologica.model';
import { VariavelMeteorologicaService } from './variavel-meteorologica.service';

@Component({
  selector: 'jhi-variavel-meteorologica-update',
  templateUrl: './variavel-meteorologica-update.component.html',
})
export class VariavelMeteorologicaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(
    protected variavelMeteorologicaService: VariavelMeteorologicaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ variavelMeteorologica }) => {
      if (!variavelMeteorologica.id) {
        const today = moment().startOf('day');
        variavelMeteorologica.created = today;
        variavelMeteorologica.updated = today;
      }

      this.updateForm(variavelMeteorologica);
    });
  }

  updateForm(variavelMeteorologica: IVariavelMeteorologica): void {
    this.editForm.patchValue({
      id: variavelMeteorologica.id,
      name: variavelMeteorologica.name,
      descricao: variavelMeteorologica.descricao,
      created: variavelMeteorologica.created ? variavelMeteorologica.created.format(DATE_TIME_FORMAT) : null,
      updated: variavelMeteorologica.updated ? variavelMeteorologica.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const variavelMeteorologica = this.createFromForm();
    if (variavelMeteorologica.id !== undefined) {
      this.subscribeToSaveResponse(this.variavelMeteorologicaService.update(variavelMeteorologica));
    } else {
      this.subscribeToSaveResponse(this.variavelMeteorologicaService.create(variavelMeteorologica));
    }
  }

  private createFromForm(): IVariavelMeteorologica {
    return {
      ...new VariavelMeteorologica(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVariavelMeteorologica>>): void {
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

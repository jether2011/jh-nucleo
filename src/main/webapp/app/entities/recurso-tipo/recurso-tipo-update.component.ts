import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRecursoTipo, RecursoTipo } from 'app/shared/model/recurso-tipo.model';
import { RecursoTipoService } from './recurso-tipo.service';

@Component({
  selector: 'jhi-recurso-tipo-update',
  templateUrl: './recurso-tipo-update.component.html',
})
export class RecursoTipoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected recursoTipoService: RecursoTipoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recursoTipo }) => {
      if (!recursoTipo.id) {
        const today = moment().startOf('day');
        recursoTipo.created = today;
        recursoTipo.updated = today;
      }

      this.updateForm(recursoTipo);
    });
  }

  updateForm(recursoTipo: IRecursoTipo): void {
    this.editForm.patchValue({
      id: recursoTipo.id,
      name: recursoTipo.name,
      descricao: recursoTipo.descricao,
      created: recursoTipo.created ? recursoTipo.created.format(DATE_TIME_FORMAT) : null,
      updated: recursoTipo.updated ? recursoTipo.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const recursoTipo = this.createFromForm();
    if (recursoTipo.id !== undefined) {
      this.subscribeToSaveResponse(this.recursoTipoService.update(recursoTipo));
    } else {
      this.subscribeToSaveResponse(this.recursoTipoService.create(recursoTipo));
    }
  }

  private createFromForm(): IRecursoTipo {
    return {
      ...new RecursoTipo(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecursoTipo>>): void {
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

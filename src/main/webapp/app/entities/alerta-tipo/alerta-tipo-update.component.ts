import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAlertaTipo, AlertaTipo } from 'app/shared/model/alerta-tipo.model';
import { AlertaTipoService } from './alerta-tipo.service';

@Component({
  selector: 'jhi-alerta-tipo-update',
  templateUrl: './alerta-tipo-update.component.html',
})
export class AlertaTipoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected alertaTipoService: AlertaTipoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alertaTipo }) => {
      if (!alertaTipo.id) {
        const today = moment().startOf('day');
        alertaTipo.created = today;
        alertaTipo.updated = today;
      }

      this.updateForm(alertaTipo);
    });
  }

  updateForm(alertaTipo: IAlertaTipo): void {
    this.editForm.patchValue({
      id: alertaTipo.id,
      nome: alertaTipo.nome,
      descricao: alertaTipo.descricao,
      created: alertaTipo.created ? alertaTipo.created.format(DATE_TIME_FORMAT) : null,
      updated: alertaTipo.updated ? alertaTipo.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alertaTipo = this.createFromForm();
    if (alertaTipo.id !== undefined) {
      this.subscribeToSaveResponse(this.alertaTipoService.update(alertaTipo));
    } else {
      this.subscribeToSaveResponse(this.alertaTipoService.create(alertaTipo));
    }
  }

  private createFromForm(): IAlertaTipo {
    return {
      ...new AlertaTipo(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlertaTipo>>): void {
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

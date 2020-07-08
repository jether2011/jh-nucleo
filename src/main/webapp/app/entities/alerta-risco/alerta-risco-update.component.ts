import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAlertaRisco, AlertaRisco } from 'app/shared/model/alerta-risco.model';
import { AlertaRiscoService } from './alerta-risco.service';

@Component({
  selector: 'jhi-alerta-risco-update',
  templateUrl: './alerta-risco-update.component.html',
})
export class AlertaRiscoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected alertaRiscoService: AlertaRiscoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alertaRisco }) => {
      if (!alertaRisco.id) {
        const today = moment().startOf('day');
        alertaRisco.created = today;
        alertaRisco.updated = today;
      }

      this.updateForm(alertaRisco);
    });
  }

  updateForm(alertaRisco: IAlertaRisco): void {
    this.editForm.patchValue({
      id: alertaRisco.id,
      nome: alertaRisco.nome,
      descricao: alertaRisco.descricao,
      created: alertaRisco.created ? alertaRisco.created.format(DATE_TIME_FORMAT) : null,
      updated: alertaRisco.updated ? alertaRisco.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alertaRisco = this.createFromForm();
    if (alertaRisco.id !== undefined) {
      this.subscribeToSaveResponse(this.alertaRiscoService.update(alertaRisco));
    } else {
      this.subscribeToSaveResponse(this.alertaRiscoService.create(alertaRisco));
    }
  }

  private createFromForm(): IAlertaRisco {
    return {
      ...new AlertaRisco(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlertaRisco>>): void {
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

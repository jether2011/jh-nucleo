import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPlanoStatus, PlanoStatus } from 'app/shared/model/plano-status.model';
import { PlanoStatusService } from './plano-status.service';

@Component({
  selector: 'jhi-plano-status-update',
  templateUrl: './plano-status-update.component.html',
})
export class PlanoStatusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected planoStatusService: PlanoStatusService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoStatus }) => {
      if (!planoStatus.id) {
        const today = moment().startOf('day');
        planoStatus.created = today;
        planoStatus.updated = today;
      }

      this.updateForm(planoStatus);
    });
  }

  updateForm(planoStatus: IPlanoStatus): void {
    this.editForm.patchValue({
      id: planoStatus.id,
      name: planoStatus.name,
      descricao: planoStatus.descricao,
      created: planoStatus.created ? planoStatus.created.format(DATE_TIME_FORMAT) : null,
      updated: planoStatus.updated ? planoStatus.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const planoStatus = this.createFromForm();
    if (planoStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.planoStatusService.update(planoStatus));
    } else {
      this.subscribeToSaveResponse(this.planoStatusService.create(planoStatus));
    }
  }

  private createFromForm(): IPlanoStatus {
    return {
      ...new PlanoStatus(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoStatus>>): void {
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

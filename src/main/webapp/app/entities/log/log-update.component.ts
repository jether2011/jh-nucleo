import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILog, Log } from 'app/shared/model/log.model';
import { LogService } from './log.service';

@Component({
  selector: 'jhi-log-update',
  templateUrl: './log-update.component.html',
})
export class LogUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    messagem: [],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected logService: LogService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ log }) => {
      if (!log.id) {
        const today = moment().startOf('day');
        log.created = today;
        log.updated = today;
      }

      this.updateForm(log);
    });
  }

  updateForm(log: ILog): void {
    this.editForm.patchValue({
      id: log.id,
      messagem: log.messagem,
      created: log.created ? log.created.format(DATE_TIME_FORMAT) : null,
      updated: log.updated ? log.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const log = this.createFromForm();
    if (log.id !== undefined) {
      this.subscribeToSaveResponse(this.logService.update(log));
    } else {
      this.subscribeToSaveResponse(this.logService.create(log));
    }
  }

  private createFromForm(): ILog {
    return {
      ...new Log(),
      id: this.editForm.get(['id'])!.value,
      messagem: this.editForm.get(['messagem'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILog>>): void {
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

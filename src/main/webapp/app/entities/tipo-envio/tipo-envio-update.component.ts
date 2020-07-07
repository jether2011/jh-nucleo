import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITipoEnvio, TipoEnvio } from 'app/shared/model/tipo-envio.model';
import { TipoEnvioService } from './tipo-envio.service';

@Component({
  selector: 'jhi-tipo-envio-update',
  templateUrl: './tipo-envio-update.component.html',
})
export class TipoEnvioUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected tipoEnvioService: TipoEnvioService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoEnvio }) => {
      if (!tipoEnvio.id) {
        const today = moment().startOf('day');
        tipoEnvio.created = today;
        tipoEnvio.updated = today;
      }

      this.updateForm(tipoEnvio);
    });
  }

  updateForm(tipoEnvio: ITipoEnvio): void {
    this.editForm.patchValue({
      id: tipoEnvio.id,
      name: tipoEnvio.name,
      descricao: tipoEnvio.descricao,
      created: tipoEnvio.created ? tipoEnvio.created.format(DATE_TIME_FORMAT) : null,
      updated: tipoEnvio.updated ? tipoEnvio.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoEnvio = this.createFromForm();
    if (tipoEnvio.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoEnvioService.update(tipoEnvio));
    } else {
      this.subscribeToSaveResponse(this.tipoEnvioService.create(tipoEnvio));
    }
  }

  private createFromForm(): ITipoEnvio {
    return {
      ...new TipoEnvio(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoEnvio>>): void {
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

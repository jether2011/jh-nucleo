import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDescargaTipo, DescargaTipo } from 'app/shared/model/descarga-tipo.model';
import { DescargaTipoService } from './descarga-tipo.service';

@Component({
  selector: 'jhi-descarga-tipo-update',
  templateUrl: './descarga-tipo-update.component.html',
})
export class DescargaTipoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected descargaTipoService: DescargaTipoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ descargaTipo }) => {
      if (!descargaTipo.id) {
        const today = moment().startOf('day');
        descargaTipo.created = today;
        descargaTipo.updated = today;
      }

      this.updateForm(descargaTipo);
    });
  }

  updateForm(descargaTipo: IDescargaTipo): void {
    this.editForm.patchValue({
      id: descargaTipo.id,
      nome: descargaTipo.nome,
      descricao: descargaTipo.descricao,
      created: descargaTipo.created ? descargaTipo.created.format(DATE_TIME_FORMAT) : null,
      updated: descargaTipo.updated ? descargaTipo.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const descargaTipo = this.createFromForm();
    if (descargaTipo.id !== undefined) {
      this.subscribeToSaveResponse(this.descargaTipoService.update(descargaTipo));
    } else {
      this.subscribeToSaveResponse(this.descargaTipoService.create(descargaTipo));
    }
  }

  private createFromForm(): IDescargaTipo {
    return {
      ...new DescargaTipo(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDescargaTipo>>): void {
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

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDescargaUnidade, DescargaUnidade } from 'app/shared/model/descarga-unidade.model';
import { DescargaUnidadeService } from './descarga-unidade.service';

@Component({
  selector: 'jhi-descarga-unidade-update',
  templateUrl: './descarga-unidade-update.component.html',
})
export class DescargaUnidadeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    unidade: [null, [Validators.required]],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(
    protected descargaUnidadeService: DescargaUnidadeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ descargaUnidade }) => {
      if (!descargaUnidade.id) {
        const today = moment().startOf('day');
        descargaUnidade.created = today;
        descargaUnidade.updated = today;
      }

      this.updateForm(descargaUnidade);
    });
  }

  updateForm(descargaUnidade: IDescargaUnidade): void {
    this.editForm.patchValue({
      id: descargaUnidade.id,
      nome: descargaUnidade.nome,
      descricao: descargaUnidade.descricao,
      unidade: descargaUnidade.unidade,
      created: descargaUnidade.created ? descargaUnidade.created.format(DATE_TIME_FORMAT) : null,
      updated: descargaUnidade.updated ? descargaUnidade.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const descargaUnidade = this.createFromForm();
    if (descargaUnidade.id !== undefined) {
      this.subscribeToSaveResponse(this.descargaUnidadeService.update(descargaUnidade));
    } else {
      this.subscribeToSaveResponse(this.descargaUnidadeService.create(descargaUnidade));
    }
  }

  private createFromForm(): IDescargaUnidade {
    return {
      ...new DescargaUnidade(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      unidade: this.editForm.get(['unidade'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDescargaUnidade>>): void {
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

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITempestadeProbabilidade, TempestadeProbabilidade } from 'app/shared/model/tempestade-probabilidade.model';
import { TempestadeProbabilidadeService } from './tempestade-probabilidade.service';

@Component({
  selector: 'jhi-tempestade-probabilidade-update',
  templateUrl: './tempestade-probabilidade-update.component.html',
})
export class TempestadeProbabilidadeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    faixa: [null, [Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(
    protected tempestadeProbabilidadeService: TempestadeProbabilidadeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tempestadeProbabilidade }) => {
      if (!tempestadeProbabilidade.id) {
        const today = moment().startOf('day');
        tempestadeProbabilidade.created = today;
        tempestadeProbabilidade.updated = today;
      }

      this.updateForm(tempestadeProbabilidade);
    });
  }

  updateForm(tempestadeProbabilidade: ITempestadeProbabilidade): void {
    this.editForm.patchValue({
      id: tempestadeProbabilidade.id,
      name: tempestadeProbabilidade.name,
      descricao: tempestadeProbabilidade.descricao,
      faixa: tempestadeProbabilidade.faixa,
      created: tempestadeProbabilidade.created ? tempestadeProbabilidade.created.format(DATE_TIME_FORMAT) : null,
      updated: tempestadeProbabilidade.updated ? tempestadeProbabilidade.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tempestadeProbabilidade = this.createFromForm();
    if (tempestadeProbabilidade.id !== undefined) {
      this.subscribeToSaveResponse(this.tempestadeProbabilidadeService.update(tempestadeProbabilidade));
    } else {
      this.subscribeToSaveResponse(this.tempestadeProbabilidadeService.create(tempestadeProbabilidade));
    }
  }

  private createFromForm(): ITempestadeProbabilidade {
    return {
      ...new TempestadeProbabilidade(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      faixa: this.editForm.get(['faixa'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITempestadeProbabilidade>>): void {
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

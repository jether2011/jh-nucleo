import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDiaSemana, DiaSemana } from 'app/shared/model/dia-semana.model';
import { DiaSemanaService } from './dia-semana.service';

@Component({
  selector: 'jhi-dia-semana-update',
  templateUrl: './dia-semana-update.component.html',
})
export class DiaSemanaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected diaSemanaService: DiaSemanaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ diaSemana }) => {
      if (!diaSemana.id) {
        const today = moment().startOf('day');
        diaSemana.created = today;
        diaSemana.updated = today;
      }

      this.updateForm(diaSemana);
    });
  }

  updateForm(diaSemana: IDiaSemana): void {
    this.editForm.patchValue({
      id: diaSemana.id,
      nome: diaSemana.nome,
      descricao: diaSemana.descricao,
      created: diaSemana.created ? diaSemana.created.format(DATE_TIME_FORMAT) : null,
      updated: diaSemana.updated ? diaSemana.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const diaSemana = this.createFromForm();
    if (diaSemana.id !== undefined) {
      this.subscribeToSaveResponse(this.diaSemanaService.update(diaSemana));
    } else {
      this.subscribeToSaveResponse(this.diaSemanaService.create(diaSemana));
    }
  }

  private createFromForm(): IDiaSemana {
    return {
      ...new DiaSemana(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiaSemana>>): void {
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

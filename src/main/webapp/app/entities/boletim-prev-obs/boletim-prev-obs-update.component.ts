import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBoletimPrevObs, BoletimPrevObs } from 'app/shared/model/boletim-prev-obs.model';
import { BoletimPrevObsService } from './boletim-prev-obs.service';

@Component({
  selector: 'jhi-boletim-prev-obs-update',
  templateUrl: './boletim-prev-obs-update.component.html',
})
export class BoletimPrevObsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected boletimPrevObsService: BoletimPrevObsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ boletimPrevObs }) => {
      if (!boletimPrevObs.id) {
        const today = moment().startOf('day');
        boletimPrevObs.created = today;
        boletimPrevObs.updated = today;
      }

      this.updateForm(boletimPrevObs);
    });
  }

  updateForm(boletimPrevObs: IBoletimPrevObs): void {
    this.editForm.patchValue({
      id: boletimPrevObs.id,
      nome: boletimPrevObs.nome,
      descricao: boletimPrevObs.descricao,
      created: boletimPrevObs.created ? boletimPrevObs.created.format(DATE_TIME_FORMAT) : null,
      updated: boletimPrevObs.updated ? boletimPrevObs.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const boletimPrevObs = this.createFromForm();
    if (boletimPrevObs.id !== undefined) {
      this.subscribeToSaveResponse(this.boletimPrevObsService.update(boletimPrevObs));
    } else {
      this.subscribeToSaveResponse(this.boletimPrevObsService.create(boletimPrevObs));
    }
  }

  private createFromForm(): IBoletimPrevObs {
    return {
      ...new BoletimPrevObs(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBoletimPrevObs>>): void {
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

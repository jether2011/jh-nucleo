import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPontosCardeais, PontosCardeais } from 'app/shared/model/pontos-cardeais.model';
import { PontosCardeaisService } from './pontos-cardeais.service';

@Component({
  selector: 'jhi-pontos-cardeais-update',
  templateUrl: './pontos-cardeais-update.component.html',
})
export class PontosCardeaisUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected pontosCardeaisService: PontosCardeaisService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pontosCardeais }) => {
      if (!pontosCardeais.id) {
        const today = moment().startOf('day');
        pontosCardeais.created = today;
        pontosCardeais.updated = today;
      }

      this.updateForm(pontosCardeais);
    });
  }

  updateForm(pontosCardeais: IPontosCardeais): void {
    this.editForm.patchValue({
      id: pontosCardeais.id,
      name: pontosCardeais.name,
      descricao: pontosCardeais.descricao,
      created: pontosCardeais.created ? pontosCardeais.created.format(DATE_TIME_FORMAT) : null,
      updated: pontosCardeais.updated ? pontosCardeais.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pontosCardeais = this.createFromForm();
    if (pontosCardeais.id !== undefined) {
      this.subscribeToSaveResponse(this.pontosCardeaisService.update(pontosCardeais));
    } else {
      this.subscribeToSaveResponse(this.pontosCardeaisService.create(pontosCardeais));
    }
  }

  private createFromForm(): IPontosCardeais {
    return {
      ...new PontosCardeais(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPontosCardeais>>): void {
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

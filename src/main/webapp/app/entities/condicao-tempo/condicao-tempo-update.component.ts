import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICondicaoTempo, CondicaoTempo } from 'app/shared/model/condicao-tempo.model';
import { CondicaoTempoService } from './condicao-tempo.service';

@Component({
  selector: 'jhi-condicao-tempo-update',
  templateUrl: './condicao-tempo-update.component.html',
})
export class CondicaoTempoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected condicaoTempoService: CondicaoTempoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ condicaoTempo }) => {
      if (!condicaoTempo.id) {
        const today = moment().startOf('day');
        condicaoTempo.created = today;
        condicaoTempo.updated = today;
      }

      this.updateForm(condicaoTempo);
    });
  }

  updateForm(condicaoTempo: ICondicaoTempo): void {
    this.editForm.patchValue({
      id: condicaoTempo.id,
      nome: condicaoTempo.nome,
      descricao: condicaoTempo.descricao,
      created: condicaoTempo.created ? condicaoTempo.created.format(DATE_TIME_FORMAT) : null,
      updated: condicaoTempo.updated ? condicaoTempo.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const condicaoTempo = this.createFromForm();
    if (condicaoTempo.id !== undefined) {
      this.subscribeToSaveResponse(this.condicaoTempoService.update(condicaoTempo));
    } else {
      this.subscribeToSaveResponse(this.condicaoTempoService.create(condicaoTempo));
    }
  }

  private createFromForm(): ICondicaoTempo {
    return {
      ...new CondicaoTempo(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICondicaoTempo>>): void {
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

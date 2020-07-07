import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IIntegrador, Integrador } from 'app/shared/model/integrador.model';
import { IntegradorService } from './integrador.service';

@Component({
  selector: 'jhi-integrador-update',
  templateUrl: './integrador-update.component.html',
})
export class IntegradorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected integradorService: IntegradorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ integrador }) => {
      if (!integrador.id) {
        const today = moment().startOf('day');
        integrador.created = today;
        integrador.updated = today;
      }

      this.updateForm(integrador);
    });
  }

  updateForm(integrador: IIntegrador): void {
    this.editForm.patchValue({
      id: integrador.id,
      nome: integrador.nome,
      descricao: integrador.descricao,
      created: integrador.created ? integrador.created.format(DATE_TIME_FORMAT) : null,
      updated: integrador.updated ? integrador.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const integrador = this.createFromForm();
    if (integrador.id !== undefined) {
      this.subscribeToSaveResponse(this.integradorService.update(integrador));
    } else {
      this.subscribeToSaveResponse(this.integradorService.create(integrador));
    }
  }

  private createFromForm(): IIntegrador {
    return {
      ...new Integrador(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIntegrador>>): void {
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

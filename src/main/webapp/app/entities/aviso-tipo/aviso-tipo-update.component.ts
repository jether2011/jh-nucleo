import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAvisoTipo, AvisoTipo } from 'app/shared/model/aviso-tipo.model';
import { AvisoTipoService } from './aviso-tipo.service';

@Component({
  selector: 'jhi-aviso-tipo-update',
  templateUrl: './aviso-tipo-update.component.html',
})
export class AvisoTipoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected avisoTipoService: AvisoTipoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avisoTipo }) => {
      if (!avisoTipo.id) {
        const today = moment().startOf('day');
        avisoTipo.created = today;
        avisoTipo.updated = today;
      }

      this.updateForm(avisoTipo);
    });
  }

  updateForm(avisoTipo: IAvisoTipo): void {
    this.editForm.patchValue({
      id: avisoTipo.id,
      nome: avisoTipo.nome,
      descricao: avisoTipo.descricao,
      created: avisoTipo.created ? avisoTipo.created.format(DATE_TIME_FORMAT) : null,
      updated: avisoTipo.updated ? avisoTipo.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const avisoTipo = this.createFromForm();
    if (avisoTipo.id !== undefined) {
      this.subscribeToSaveResponse(this.avisoTipoService.update(avisoTipo));
    } else {
      this.subscribeToSaveResponse(this.avisoTipoService.create(avisoTipo));
    }
  }

  private createFromForm(): IAvisoTipo {
    return {
      ...new AvisoTipo(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvisoTipo>>): void {
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

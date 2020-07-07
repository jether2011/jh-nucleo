import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITipoFerramenta, TipoFerramenta } from 'app/shared/model/tipo-ferramenta.model';
import { TipoFerramentaService } from './tipo-ferramenta.service';

@Component({
  selector: 'jhi-tipo-ferramenta-update',
  templateUrl: './tipo-ferramenta-update.component.html',
})
export class TipoFerramentaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected tipoFerramentaService: TipoFerramentaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoFerramenta }) => {
      if (!tipoFerramenta.id) {
        const today = moment().startOf('day');
        tipoFerramenta.created = today;
        tipoFerramenta.updated = today;
      }

      this.updateForm(tipoFerramenta);
    });
  }

  updateForm(tipoFerramenta: ITipoFerramenta): void {
    this.editForm.patchValue({
      id: tipoFerramenta.id,
      name: tipoFerramenta.name,
      descricao: tipoFerramenta.descricao,
      created: tipoFerramenta.created ? tipoFerramenta.created.format(DATE_TIME_FORMAT) : null,
      updated: tipoFerramenta.updated ? tipoFerramenta.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoFerramenta = this.createFromForm();
    if (tipoFerramenta.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoFerramentaService.update(tipoFerramenta));
    } else {
      this.subscribeToSaveResponse(this.tipoFerramentaService.create(tipoFerramenta));
    }
  }

  private createFromForm(): ITipoFerramenta {
    return {
      ...new TipoFerramenta(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoFerramenta>>): void {
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

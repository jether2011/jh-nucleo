import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IIntensidadeChuva, IntensidadeChuva } from 'app/shared/model/intensidade-chuva.model';
import { IntensidadeChuvaService } from './intensidade-chuva.service';

@Component({
  selector: 'jhi-intensidade-chuva-update',
  templateUrl: './intensidade-chuva-update.component.html',
})
export class IntensidadeChuvaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    faixa: [null, [Validators.required, Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(
    protected intensidadeChuvaService: IntensidadeChuvaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ intensidadeChuva }) => {
      if (!intensidadeChuva.id) {
        const today = moment().startOf('day');
        intensidadeChuva.created = today;
        intensidadeChuva.updated = today;
      }

      this.updateForm(intensidadeChuva);
    });
  }

  updateForm(intensidadeChuva: IIntensidadeChuva): void {
    this.editForm.patchValue({
      id: intensidadeChuva.id,
      nome: intensidadeChuva.nome,
      descricao: intensidadeChuva.descricao,
      faixa: intensidadeChuva.faixa,
      created: intensidadeChuva.created ? intensidadeChuva.created.format(DATE_TIME_FORMAT) : null,
      updated: intensidadeChuva.updated ? intensidadeChuva.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const intensidadeChuva = this.createFromForm();
    if (intensidadeChuva.id !== undefined) {
      this.subscribeToSaveResponse(this.intensidadeChuvaService.update(intensidadeChuva));
    } else {
      this.subscribeToSaveResponse(this.intensidadeChuvaService.create(intensidadeChuva));
    }
  }

  private createFromForm(): IIntensidadeChuva {
    return {
      ...new IntensidadeChuva(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      faixa: this.editForm.get(['faixa'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIntensidadeChuva>>): void {
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

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAcumuladoChuvaFaixa, AcumuladoChuvaFaixa } from 'app/shared/model/acumulado-chuva-faixa.model';
import { AcumuladoChuvaFaixaService } from './acumulado-chuva-faixa.service';

@Component({
  selector: 'jhi-acumulado-chuva-faixa-update',
  templateUrl: './acumulado-chuva-faixa-update.component.html',
})
export class AcumuladoChuvaFaixaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    deMm: [null, [Validators.required]],
    ateMm: [null, [Validators.required]],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(
    protected acumuladoChuvaFaixaService: AcumuladoChuvaFaixaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ acumuladoChuvaFaixa }) => {
      if (!acumuladoChuvaFaixa.id) {
        const today = moment().startOf('day');
        acumuladoChuvaFaixa.created = today;
        acumuladoChuvaFaixa.updated = today;
      }

      this.updateForm(acumuladoChuvaFaixa);
    });
  }

  updateForm(acumuladoChuvaFaixa: IAcumuladoChuvaFaixa): void {
    this.editForm.patchValue({
      id: acumuladoChuvaFaixa.id,
      nome: acumuladoChuvaFaixa.nome,
      descricao: acumuladoChuvaFaixa.descricao,
      deMm: acumuladoChuvaFaixa.deMm,
      ateMm: acumuladoChuvaFaixa.ateMm,
      created: acumuladoChuvaFaixa.created ? acumuladoChuvaFaixa.created.format(DATE_TIME_FORMAT) : null,
      updated: acumuladoChuvaFaixa.updated ? acumuladoChuvaFaixa.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const acumuladoChuvaFaixa = this.createFromForm();
    if (acumuladoChuvaFaixa.id !== undefined) {
      this.subscribeToSaveResponse(this.acumuladoChuvaFaixaService.update(acumuladoChuvaFaixa));
    } else {
      this.subscribeToSaveResponse(this.acumuladoChuvaFaixaService.create(acumuladoChuvaFaixa));
    }
  }

  private createFromForm(): IAcumuladoChuvaFaixa {
    return {
      ...new AcumuladoChuvaFaixa(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      deMm: this.editForm.get(['deMm'])!.value,
      ateMm: this.editForm.get(['ateMm'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAcumuladoChuvaFaixa>>): void {
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

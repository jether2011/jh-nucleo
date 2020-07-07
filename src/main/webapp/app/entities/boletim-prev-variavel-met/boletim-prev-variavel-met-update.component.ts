import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBoletimPrevVariavelMet, BoletimPrevVariavelMet } from 'app/shared/model/boletim-prev-variavel-met.model';
import { BoletimPrevVariavelMetService } from './boletim-prev-variavel-met.service';
import { IBoletimPrevisao } from 'app/shared/model/boletim-previsao.model';
import { BoletimPrevisaoService } from 'app/entities/boletim-previsao/boletim-previsao.service';
import { IVariavelMeteorologica } from 'app/shared/model/variavel-meteorologica.model';
import { VariavelMeteorologicaService } from 'app/entities/variavel-meteorologica/variavel-meteorologica.service';

type SelectableEntity = IBoletimPrevisao | IVariavelMeteorologica;

@Component({
  selector: 'jhi-boletim-prev-variavel-met-update',
  templateUrl: './boletim-prev-variavel-met-update.component.html',
})
export class BoletimPrevVariavelMetUpdateComponent implements OnInit {
  isSaving = false;
  boletimprevisaos: IBoletimPrevisao[] = [];
  variavelmeteorologicas: IVariavelMeteorologica[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    created: [null, [Validators.required]],
    updated: [],
    boletimPrevisaoId: [],
    variavelMeteorologicaId: [],
  });

  constructor(
    protected boletimPrevVariavelMetService: BoletimPrevVariavelMetService,
    protected boletimPrevisaoService: BoletimPrevisaoService,
    protected variavelMeteorologicaService: VariavelMeteorologicaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ boletimPrevVariavelMet }) => {
      if (!boletimPrevVariavelMet.id) {
        const today = moment().startOf('day');
        boletimPrevVariavelMet.created = today;
        boletimPrevVariavelMet.updated = today;
      }

      this.updateForm(boletimPrevVariavelMet);

      this.boletimPrevisaoService.query().subscribe((res: HttpResponse<IBoletimPrevisao[]>) => (this.boletimprevisaos = res.body || []));

      this.variavelMeteorologicaService
        .query()
        .subscribe((res: HttpResponse<IVariavelMeteorologica[]>) => (this.variavelmeteorologicas = res.body || []));
    });
  }

  updateForm(boletimPrevVariavelMet: IBoletimPrevVariavelMet): void {
    this.editForm.patchValue({
      id: boletimPrevVariavelMet.id,
      nome: boletimPrevVariavelMet.nome,
      descricao: boletimPrevVariavelMet.descricao,
      created: boletimPrevVariavelMet.created ? boletimPrevVariavelMet.created.format(DATE_TIME_FORMAT) : null,
      updated: boletimPrevVariavelMet.updated ? boletimPrevVariavelMet.updated.format(DATE_TIME_FORMAT) : null,
      boletimPrevisaoId: boletimPrevVariavelMet.boletimPrevisaoId,
      variavelMeteorologicaId: boletimPrevVariavelMet.variavelMeteorologicaId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const boletimPrevVariavelMet = this.createFromForm();
    if (boletimPrevVariavelMet.id !== undefined) {
      this.subscribeToSaveResponse(this.boletimPrevVariavelMetService.update(boletimPrevVariavelMet));
    } else {
      this.subscribeToSaveResponse(this.boletimPrevVariavelMetService.create(boletimPrevVariavelMet));
    }
  }

  private createFromForm(): IBoletimPrevVariavelMet {
    return {
      ...new BoletimPrevVariavelMet(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      boletimPrevisaoId: this.editForm.get(['boletimPrevisaoId'])!.value,
      variavelMeteorologicaId: this.editForm.get(['variavelMeteorologicaId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBoletimPrevVariavelMet>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}

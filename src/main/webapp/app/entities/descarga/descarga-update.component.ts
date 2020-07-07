import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDescarga, Descarga } from 'app/shared/model/descarga.model';
import { DescargaService } from './descarga.service';
import { IRede } from 'app/shared/model/rede.model';
import { RedeService } from 'app/entities/rede/rede.service';
import { IDescargaTipo } from 'app/shared/model/descarga-tipo.model';
import { DescargaTipoService } from 'app/entities/descarga-tipo/descarga-tipo.service';
import { IDescargaUnidade } from 'app/shared/model/descarga-unidade.model';
import { DescargaUnidadeService } from 'app/entities/descarga-unidade/descarga-unidade.service';
import { IAlerta } from 'app/shared/model/alerta.model';
import { AlertaService } from 'app/entities/alerta/alerta.service';

type SelectableEntity = IRede | IDescargaTipo | IDescargaUnidade | IAlerta;

@Component({
  selector: 'jhi-descarga-update',
  templateUrl: './descarga-update.component.html',
})
export class DescargaUpdateComponent implements OnInit {
  isSaving = false;
  redes: IRede[] = [];
  descargatipos: IDescargaTipo[] = [];
  descargaunidades: IDescargaUnidade[] = [];
  alertas: IAlerta[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    qtd: [],
    dataPrimeiraDescarga: [null, [Validators.required]],
    tempoAntecipacao: [
      null,
      [Validators.minLength(8), Validators.maxLength(8), Validators.pattern('^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$')],
    ],
    created: [null, [Validators.required]],
    updated: [],
    redeId: [],
    descargaTipoId: [],
    descargaUnidadeId: [],
    alertaId: [],
  });

  constructor(
    protected descargaService: DescargaService,
    protected redeService: RedeService,
    protected descargaTipoService: DescargaTipoService,
    protected descargaUnidadeService: DescargaUnidadeService,
    protected alertaService: AlertaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ descarga }) => {
      if (!descarga.id) {
        const today = moment().startOf('day');
        descarga.dataPrimeiraDescarga = today;
        descarga.created = today;
        descarga.updated = today;
      }

      this.updateForm(descarga);

      this.redeService.query().subscribe((res: HttpResponse<IRede[]>) => (this.redes = res.body || []));

      this.descargaTipoService.query().subscribe((res: HttpResponse<IDescargaTipo[]>) => (this.descargatipos = res.body || []));

      this.descargaUnidadeService.query().subscribe((res: HttpResponse<IDescargaUnidade[]>) => (this.descargaunidades = res.body || []));

      this.alertaService.query().subscribe((res: HttpResponse<IAlerta[]>) => (this.alertas = res.body || []));
    });
  }

  updateForm(descarga: IDescarga): void {
    this.editForm.patchValue({
      id: descarga.id,
      nome: descarga.nome,
      descricao: descarga.descricao,
      qtd: descarga.qtd,
      dataPrimeiraDescarga: descarga.dataPrimeiraDescarga ? descarga.dataPrimeiraDescarga.format(DATE_TIME_FORMAT) : null,
      tempoAntecipacao: descarga.tempoAntecipacao,
      created: descarga.created ? descarga.created.format(DATE_TIME_FORMAT) : null,
      updated: descarga.updated ? descarga.updated.format(DATE_TIME_FORMAT) : null,
      redeId: descarga.redeId,
      descargaTipoId: descarga.descargaTipoId,
      descargaUnidadeId: descarga.descargaUnidadeId,
      alertaId: descarga.alertaId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const descarga = this.createFromForm();
    if (descarga.id !== undefined) {
      this.subscribeToSaveResponse(this.descargaService.update(descarga));
    } else {
      this.subscribeToSaveResponse(this.descargaService.create(descarga));
    }
  }

  private createFromForm(): IDescarga {
    return {
      ...new Descarga(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      qtd: this.editForm.get(['qtd'])!.value,
      dataPrimeiraDescarga: this.editForm.get(['dataPrimeiraDescarga'])!.value
        ? moment(this.editForm.get(['dataPrimeiraDescarga'])!.value, DATE_TIME_FORMAT)
        : undefined,
      tempoAntecipacao: this.editForm.get(['tempoAntecipacao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      redeId: this.editForm.get(['redeId'])!.value,
      descargaTipoId: this.editForm.get(['descargaTipoId'])!.value,
      descargaUnidadeId: this.editForm.get(['descargaUnidadeId'])!.value,
      alertaId: this.editForm.get(['alertaId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDescarga>>): void {
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

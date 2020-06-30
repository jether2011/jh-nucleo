import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBoletimPrevisao, BoletimPrevisao } from 'app/shared/model/boletim-previsao.model';
import { BoletimPrevisaoService } from './boletim-previsao.service';
import { IBoletim } from 'app/shared/model/boletim.model';
import { BoletimService } from 'app/entities/boletim/boletim.service';
import { IBoletimPrevObs } from 'app/shared/model/boletim-prev-obs.model';
import { BoletimPrevObsService } from 'app/entities/boletim-prev-obs/boletim-prev-obs.service';
import { IIntensidadeChuva } from 'app/shared/model/intensidade-chuva.model';
import { IntensidadeChuvaService } from 'app/entities/intensidade-chuva/intensidade-chuva.service';
import { IUmidadeAr } from 'app/shared/model/umidade-ar.model';
import { UmidadeArService } from 'app/entities/umidade-ar/umidade-ar.service';
import { IAlvo } from 'app/shared/model/alvo.model';
import { AlvoService } from 'app/entities/alvo/alvo.service';
import { IPontosCardeais } from 'app/shared/model/pontos-cardeais.model';
import { PontosCardeaisService } from 'app/entities/pontos-cardeais/pontos-cardeais.service';
import { IVentoVmFaixa } from 'app/shared/model/vento-vm-faixa.model';
import { VentoVmFaixaService } from 'app/entities/vento-vm-faixa/vento-vm-faixa.service';
import { ITempestadeProbabilidade } from 'app/shared/model/tempestade-probabilidade.model';
import { TempestadeProbabilidadeService } from 'app/entities/tempestade-probabilidade/tempestade-probabilidade.service';
import { ITempestadeNivel } from 'app/shared/model/tempestade-nivel.model';
import { TempestadeNivelService } from 'app/entities/tempestade-nivel/tempestade-nivel.service';
import { IAcumuladoChuvaFaixa } from 'app/shared/model/acumulado-chuva-faixa.model';
import { AcumuladoChuvaFaixaService } from 'app/entities/acumulado-chuva-faixa/acumulado-chuva-faixa.service';
import { ICondicaoTempo } from 'app/shared/model/condicao-tempo.model';
import { CondicaoTempoService } from 'app/entities/condicao-tempo/condicao-tempo.service';

type SelectableEntity =
  | IBoletim
  | IBoletimPrevObs
  | IIntensidadeChuva
  | IUmidadeAr
  | IAlvo
  | IPontosCardeais
  | IVentoVmFaixa
  | ITempestadeProbabilidade
  | ITempestadeNivel
  | IAcumuladoChuvaFaixa
  | ICondicaoTempo;

@Component({
  selector: 'jhi-boletim-previsao-update',
  templateUrl: './boletim-previsao-update.component.html',
})
export class BoletimPrevisaoUpdateComponent implements OnInit {
  isSaving = false;
  boletims: IBoletim[] = [];
  boletimprevobs: IBoletimPrevObs[] = [];
  intensidadechuvas: IIntensidadeChuva[] = [];
  umidadears: IUmidadeAr[] = [];
  alvos: IAlvo[] = [];
  pontoscardeais: IPontosCardeais[] = [];
  ventovmfaixas: IVentoVmFaixa[] = [];
  tempestadeprobabilidades: ITempestadeProbabilidade[] = [];
  tempestadenivels: ITempestadeNivel[] = [];
  acumuladochuvafaixas: IAcumuladoChuvaFaixa[] = [];
  condicaotempos: ICondicaoTempo[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    local: [null, [Validators.maxLength(255)]],
    imgCondicaoTempo: [],
    condicaoTempo: [null, [Validators.maxLength(255)]],
    observacao: [null, [Validators.maxLength(255)]],
    grupoOrdem: [],
    ondas: [null, [Validators.maxLength(255)]],
    temperaturaDe: [],
    temperaturaAte: [],
    ventovelocidademediakmh: [],
    ventosObservacao: [null, [Validators.maxLength(255)]],
    ventoRajada: [],
    tempestadeObservacao: [null, [Validators.maxLength(255)]],
    chuvaObservacao: [null, [Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
    boletimId: [],
    boletimPrevObsId: [],
    intensidadeChuvaId: [null, Validators.required],
    umidadeArId: [],
    alvoId: [],
    ventosOrigemPontosCardeaisId: [],
    ventoRajadaVentoVmFaixaId: [],
    tempestadeProbabilidadeId: [],
    tempestadeNivelId: [],
    acumuladoChuvaFaixaId: [],
    condicaoTempoId: [],
  });

  constructor(
    protected boletimPrevisaoService: BoletimPrevisaoService,
    protected boletimService: BoletimService,
    protected boletimPrevObsService: BoletimPrevObsService,
    protected intensidadeChuvaService: IntensidadeChuvaService,
    protected umidadeArService: UmidadeArService,
    protected alvoService: AlvoService,
    protected pontosCardeaisService: PontosCardeaisService,
    protected ventoVmFaixaService: VentoVmFaixaService,
    protected tempestadeProbabilidadeService: TempestadeProbabilidadeService,
    protected tempestadeNivelService: TempestadeNivelService,
    protected acumuladoChuvaFaixaService: AcumuladoChuvaFaixaService,
    protected condicaoTempoService: CondicaoTempoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ boletimPrevisao }) => {
      if (!boletimPrevisao.id) {
        const today = moment().startOf('day');
        boletimPrevisao.created = today;
        boletimPrevisao.updated = today;
      }

      this.updateForm(boletimPrevisao);

      this.boletimService.query().subscribe((res: HttpResponse<IBoletim[]>) => (this.boletims = res.body || []));

      this.boletimPrevObsService.query().subscribe((res: HttpResponse<IBoletimPrevObs[]>) => (this.boletimprevobs = res.body || []));

      this.intensidadeChuvaService.query().subscribe((res: HttpResponse<IIntensidadeChuva[]>) => (this.intensidadechuvas = res.body || []));

      this.umidadeArService.query().subscribe((res: HttpResponse<IUmidadeAr[]>) => (this.umidadears = res.body || []));

      this.alvoService.query().subscribe((res: HttpResponse<IAlvo[]>) => (this.alvos = res.body || []));

      this.pontosCardeaisService.query().subscribe((res: HttpResponse<IPontosCardeais[]>) => (this.pontoscardeais = res.body || []));

      this.ventoVmFaixaService.query().subscribe((res: HttpResponse<IVentoVmFaixa[]>) => (this.ventovmfaixas = res.body || []));

      this.tempestadeProbabilidadeService
        .query()
        .subscribe((res: HttpResponse<ITempestadeProbabilidade[]>) => (this.tempestadeprobabilidades = res.body || []));

      this.tempestadeNivelService.query().subscribe((res: HttpResponse<ITempestadeNivel[]>) => (this.tempestadenivels = res.body || []));

      this.acumuladoChuvaFaixaService
        .query()
        .subscribe((res: HttpResponse<IAcumuladoChuvaFaixa[]>) => (this.acumuladochuvafaixas = res.body || []));

      this.condicaoTempoService.query().subscribe((res: HttpResponse<ICondicaoTempo[]>) => (this.condicaotempos = res.body || []));
    });
  }

  updateForm(boletimPrevisao: IBoletimPrevisao): void {
    this.editForm.patchValue({
      id: boletimPrevisao.id,
      nome: boletimPrevisao.nome,
      descricao: boletimPrevisao.descricao,
      local: boletimPrevisao.local,
      imgCondicaoTempo: boletimPrevisao.imgCondicaoTempo,
      condicaoTempo: boletimPrevisao.condicaoTempo,
      observacao: boletimPrevisao.observacao,
      grupoOrdem: boletimPrevisao.grupoOrdem,
      ondas: boletimPrevisao.ondas,
      temperaturaDe: boletimPrevisao.temperaturaDe,
      temperaturaAte: boletimPrevisao.temperaturaAte,
      ventovelocidademediakmh: boletimPrevisao.ventovelocidademediakmh,
      ventosObservacao: boletimPrevisao.ventosObservacao,
      ventoRajada: boletimPrevisao.ventoRajada,
      tempestadeObservacao: boletimPrevisao.tempestadeObservacao,
      chuvaObservacao: boletimPrevisao.chuvaObservacao,
      created: boletimPrevisao.created ? boletimPrevisao.created.format(DATE_TIME_FORMAT) : null,
      updated: boletimPrevisao.updated ? boletimPrevisao.updated.format(DATE_TIME_FORMAT) : null,
      boletimId: boletimPrevisao.boletimId,
      boletimPrevObsId: boletimPrevisao.boletimPrevObsId,
      intensidadeChuvaId: boletimPrevisao.intensidadeChuvaId,
      umidadeArId: boletimPrevisao.umidadeArId,
      alvoId: boletimPrevisao.alvoId,
      ventosOrigemPontosCardeaisId: boletimPrevisao.ventosOrigemPontosCardeaisId,
      ventoRajadaVentoVmFaixaId: boletimPrevisao.ventoRajadaVentoVmFaixaId,
      tempestadeProbabilidadeId: boletimPrevisao.tempestadeProbabilidadeId,
      tempestadeNivelId: boletimPrevisao.tempestadeNivelId,
      acumuladoChuvaFaixaId: boletimPrevisao.acumuladoChuvaFaixaId,
      condicaoTempoId: boletimPrevisao.condicaoTempoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const boletimPrevisao = this.createFromForm();
    if (boletimPrevisao.id !== undefined) {
      this.subscribeToSaveResponse(this.boletimPrevisaoService.update(boletimPrevisao));
    } else {
      this.subscribeToSaveResponse(this.boletimPrevisaoService.create(boletimPrevisao));
    }
  }

  private createFromForm(): IBoletimPrevisao {
    return {
      ...new BoletimPrevisao(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      local: this.editForm.get(['local'])!.value,
      imgCondicaoTempo: this.editForm.get(['imgCondicaoTempo'])!.value,
      condicaoTempo: this.editForm.get(['condicaoTempo'])!.value,
      observacao: this.editForm.get(['observacao'])!.value,
      grupoOrdem: this.editForm.get(['grupoOrdem'])!.value,
      ondas: this.editForm.get(['ondas'])!.value,
      temperaturaDe: this.editForm.get(['temperaturaDe'])!.value,
      temperaturaAte: this.editForm.get(['temperaturaAte'])!.value,
      ventovelocidademediakmh: this.editForm.get(['ventovelocidademediakmh'])!.value,
      ventosObservacao: this.editForm.get(['ventosObservacao'])!.value,
      ventoRajada: this.editForm.get(['ventoRajada'])!.value,
      tempestadeObservacao: this.editForm.get(['tempestadeObservacao'])!.value,
      chuvaObservacao: this.editForm.get(['chuvaObservacao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      boletimId: this.editForm.get(['boletimId'])!.value,
      boletimPrevObsId: this.editForm.get(['boletimPrevObsId'])!.value,
      intensidadeChuvaId: this.editForm.get(['intensidadeChuvaId'])!.value,
      umidadeArId: this.editForm.get(['umidadeArId'])!.value,
      alvoId: this.editForm.get(['alvoId'])!.value,
      ventosOrigemPontosCardeaisId: this.editForm.get(['ventosOrigemPontosCardeaisId'])!.value,
      ventoRajadaVentoVmFaixaId: this.editForm.get(['ventoRajadaVentoVmFaixaId'])!.value,
      tempestadeProbabilidadeId: this.editForm.get(['tempestadeProbabilidadeId'])!.value,
      tempestadeNivelId: this.editForm.get(['tempestadeNivelId'])!.value,
      acumuladoChuvaFaixaId: this.editForm.get(['acumuladoChuvaFaixaId'])!.value,
      condicaoTempoId: this.editForm.get(['condicaoTempoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBoletimPrevisao>>): void {
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

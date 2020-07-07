import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAlvo, Alvo } from 'app/shared/model/alvo.model';
import { AlvoService } from './alvo.service';
import { IPlano } from 'app/shared/model/plano.model';
import { PlanoService } from 'app/entities/plano/plano.service';

@Component({
  selector: 'jhi-alvo-update',
  templateUrl: './alvo-update.component.html',
})
export class AlvoUpdateComponent implements OnInit {
  isSaving = false;
  planos: IPlano[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    nomeReduzido: [null, [Validators.maxLength(255)]],
    descricao: [],
    primeiroPonto: [null, [Validators.maxLength(255)]],
    ultimoPonto: [null, [Validators.maxLength(255)]],
    horarioLiberacao: [null, [Validators.required]],
    horario: [null, [Validators.required]],
    duracao: [
      null,
      [Validators.minLength(8), Validators.maxLength(8), Validators.pattern('^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$')],
    ],
    duracaoAtual: [
      null,
      [Validators.minLength(8), Validators.maxLength(8), Validators.pattern('^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$')],
    ],
    dataDesativado: [null, [Validators.required]],
    coordenadasAlertaPontos: [null, [Validators.maxLength(255)]],
    coordenadasLiberacaoPontos: [null, [Validators.maxLength(255)]],
    telegramTokenBot: [null, [Validators.maxLength(255)]],
    telegramChatId: [null, [Validators.maxLength(255)]],
    horarioBloqueioNotificacao: [null, [Validators.required]],
    coordenadasOriginalPontos: [null, [Validators.maxLength(255)]],
    ativo: [],
    created: [null, [Validators.required]],
    updated: [],
    planoId: [],
  });

  constructor(
    protected alvoService: AlvoService,
    protected planoService: PlanoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alvo }) => {
      if (!alvo.id) {
        const today = moment().startOf('day');
        alvo.horarioLiberacao = today;
        alvo.horario = today;
        alvo.dataDesativado = today;
        alvo.horarioBloqueioNotificacao = today;
        alvo.created = today;
        alvo.updated = today;
      }

      this.updateForm(alvo);

      this.planoService.query().subscribe((res: HttpResponse<IPlano[]>) => (this.planos = res.body || []));
    });
  }

  updateForm(alvo: IAlvo): void {
    this.editForm.patchValue({
      id: alvo.id,
      nome: alvo.nome,
      nomeReduzido: alvo.nomeReduzido,
      descricao: alvo.descricao,
      primeiroPonto: alvo.primeiroPonto,
      ultimoPonto: alvo.ultimoPonto,
      horarioLiberacao: alvo.horarioLiberacao ? alvo.horarioLiberacao.format(DATE_TIME_FORMAT) : null,
      horario: alvo.horario ? alvo.horario.format(DATE_TIME_FORMAT) : null,
      duracao: alvo.duracao,
      duracaoAtual: alvo.duracaoAtual,
      dataDesativado: alvo.dataDesativado ? alvo.dataDesativado.format(DATE_TIME_FORMAT) : null,
      coordenadasAlertaPontos: alvo.coordenadasAlertaPontos,
      coordenadasLiberacaoPontos: alvo.coordenadasLiberacaoPontos,
      telegramTokenBot: alvo.telegramTokenBot,
      telegramChatId: alvo.telegramChatId,
      horarioBloqueioNotificacao: alvo.horarioBloqueioNotificacao ? alvo.horarioBloqueioNotificacao.format(DATE_TIME_FORMAT) : null,
      coordenadasOriginalPontos: alvo.coordenadasOriginalPontos,
      ativo: alvo.ativo,
      created: alvo.created ? alvo.created.format(DATE_TIME_FORMAT) : null,
      updated: alvo.updated ? alvo.updated.format(DATE_TIME_FORMAT) : null,
      planoId: alvo.planoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alvo = this.createFromForm();
    if (alvo.id !== undefined) {
      this.subscribeToSaveResponse(this.alvoService.update(alvo));
    } else {
      this.subscribeToSaveResponse(this.alvoService.create(alvo));
    }
  }

  private createFromForm(): IAlvo {
    return {
      ...new Alvo(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      nomeReduzido: this.editForm.get(['nomeReduzido'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      primeiroPonto: this.editForm.get(['primeiroPonto'])!.value,
      ultimoPonto: this.editForm.get(['ultimoPonto'])!.value,
      horarioLiberacao: this.editForm.get(['horarioLiberacao'])!.value
        ? moment(this.editForm.get(['horarioLiberacao'])!.value, DATE_TIME_FORMAT)
        : undefined,
      horario: this.editForm.get(['horario'])!.value ? moment(this.editForm.get(['horario'])!.value, DATE_TIME_FORMAT) : undefined,
      duracao: this.editForm.get(['duracao'])!.value,
      duracaoAtual: this.editForm.get(['duracaoAtual'])!.value,
      dataDesativado: this.editForm.get(['dataDesativado'])!.value
        ? moment(this.editForm.get(['dataDesativado'])!.value, DATE_TIME_FORMAT)
        : undefined,
      coordenadasAlertaPontos: this.editForm.get(['coordenadasAlertaPontos'])!.value,
      coordenadasLiberacaoPontos: this.editForm.get(['coordenadasLiberacaoPontos'])!.value,
      telegramTokenBot: this.editForm.get(['telegramTokenBot'])!.value,
      telegramChatId: this.editForm.get(['telegramChatId'])!.value,
      horarioBloqueioNotificacao: this.editForm.get(['horarioBloqueioNotificacao'])!.value
        ? moment(this.editForm.get(['horarioBloqueioNotificacao'])!.value, DATE_TIME_FORMAT)
        : undefined,
      coordenadasOriginalPontos: this.editForm.get(['coordenadasOriginalPontos'])!.value,
      ativo: this.editForm.get(['ativo'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      planoId: this.editForm.get(['planoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlvo>>): void {
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

  trackById(index: number, item: IPlano): any {
    return item.id;
  }
}

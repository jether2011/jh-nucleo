import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IContato, Contato } from 'app/shared/model/contato.model';
import { ContatoService } from './contato.service';

@Component({
  selector: 'jhi-contato-update',
  templateUrl: './contato-update.component.html',
})
export class ContatoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    email: [null, [Validators.required, Validators.maxLength(255)]],
    celular: [null, [Validators.maxLength(20)]],
    ativo: [],
    contatoAlertaTelefonico: [],
    prioridade: [],
    horaLigacaoInicial: [
      null,
      [Validators.minLength(8), Validators.maxLength(8), Validators.pattern('^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$')],
    ],
    horaLigacaoFinal: [
      null,
      [Validators.minLength(8), Validators.maxLength(8), Validators.pattern('^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$')],
    ],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected contatoService: ContatoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contato }) => {
      if (!contato.id) {
        const today = moment().startOf('day');
        contato.created = today;
        contato.updated = today;
      }

      this.updateForm(contato);
    });
  }

  updateForm(contato: IContato): void {
    this.editForm.patchValue({
      id: contato.id,
      nome: contato.nome,
      descricao: contato.descricao,
      email: contato.email,
      celular: contato.celular,
      ativo: contato.ativo,
      contatoAlertaTelefonico: contato.contatoAlertaTelefonico,
      prioridade: contato.prioridade,
      horaLigacaoInicial: contato.horaLigacaoInicial,
      horaLigacaoFinal: contato.horaLigacaoFinal,
      created: contato.created ? contato.created.format(DATE_TIME_FORMAT) : null,
      updated: contato.updated ? contato.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contato = this.createFromForm();
    if (contato.id !== undefined) {
      this.subscribeToSaveResponse(this.contatoService.update(contato));
    } else {
      this.subscribeToSaveResponse(this.contatoService.create(contato));
    }
  }

  private createFromForm(): IContato {
    return {
      ...new Contato(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      email: this.editForm.get(['email'])!.value,
      celular: this.editForm.get(['celular'])!.value,
      ativo: this.editForm.get(['ativo'])!.value,
      contatoAlertaTelefonico: this.editForm.get(['contatoAlertaTelefonico'])!.value,
      prioridade: this.editForm.get(['prioridade'])!.value,
      horaLigacaoInicial: this.editForm.get(['horaLigacaoInicial'])!.value,
      horaLigacaoFinal: this.editForm.get(['horaLigacaoFinal'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContato>>): void {
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

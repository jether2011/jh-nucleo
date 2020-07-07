import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IContatoAlvo, ContatoAlvo } from 'app/shared/model/contato-alvo.model';
import { ContatoAlvoService } from './contato-alvo.service';
import { IContato } from 'app/shared/model/contato.model';
import { ContatoService } from 'app/entities/contato/contato.service';
import { IAlvo } from 'app/shared/model/alvo.model';
import { AlvoService } from 'app/entities/alvo/alvo.service';

type SelectableEntity = IContato | IAlvo;

@Component({
  selector: 'jhi-contato-alvo-update',
  templateUrl: './contato-alvo-update.component.html',
})
export class ContatoAlvoUpdateComponent implements OnInit {
  isSaving = false;
  contatoes: IContato[] = [];
  alvos: IAlvo[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    created: [null, [Validators.required]],
    updated: [],
    contatoId: [],
    alvoId: [],
  });

  constructor(
    protected contatoAlvoService: ContatoAlvoService,
    protected contatoService: ContatoService,
    protected alvoService: AlvoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contatoAlvo }) => {
      if (!contatoAlvo.id) {
        const today = moment().startOf('day');
        contatoAlvo.created = today;
        contatoAlvo.updated = today;
      }

      this.updateForm(contatoAlvo);

      this.contatoService.query().subscribe((res: HttpResponse<IContato[]>) => (this.contatoes = res.body || []));

      this.alvoService.query().subscribe((res: HttpResponse<IAlvo[]>) => (this.alvos = res.body || []));
    });
  }

  updateForm(contatoAlvo: IContatoAlvo): void {
    this.editForm.patchValue({
      id: contatoAlvo.id,
      nome: contatoAlvo.nome,
      descricao: contatoAlvo.descricao,
      created: contatoAlvo.created ? contatoAlvo.created.format(DATE_TIME_FORMAT) : null,
      updated: contatoAlvo.updated ? contatoAlvo.updated.format(DATE_TIME_FORMAT) : null,
      contatoId: contatoAlvo.contatoId,
      alvoId: contatoAlvo.alvoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contatoAlvo = this.createFromForm();
    if (contatoAlvo.id !== undefined) {
      this.subscribeToSaveResponse(this.contatoAlvoService.update(contatoAlvo));
    } else {
      this.subscribeToSaveResponse(this.contatoAlvoService.create(contatoAlvo));
    }
  }

  private createFromForm(): IContatoAlvo {
    return {
      ...new ContatoAlvo(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      contatoId: this.editForm.get(['contatoId'])!.value,
      alvoId: this.editForm.get(['alvoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContatoAlvo>>): void {
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

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { INotificacaoEnviada, NotificacaoEnviada } from 'app/shared/model/notificacao-enviada.model';
import { NotificacaoEnviadaService } from './notificacao-enviada.service';
import { IPlanoRecurso } from 'app/shared/model/plano-recurso.model';
import { PlanoRecursoService } from 'app/entities/plano-recurso/plano-recurso.service';

@Component({
  selector: 'jhi-notificacao-enviada-update',
  templateUrl: './notificacao-enviada-update.component.html',
})
export class NotificacaoEnviadaUpdateComponent implements OnInit {
  isSaving = false;
  planorecursos: IPlanoRecurso[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    destinatarios: [null, [Validators.maxLength(255)]],
    tipo: [null, [Validators.maxLength(255)]],
    status: [null, [Validators.maxLength(255)]],
    assunto: [null, [Validators.maxLength(255)]],
    enviado: [],
    contador: [],
    amazonMessageId: [null, [Validators.maxLength(255)]],
    amazonDateLog: [null, [Validators.required]],
    priceInUsd: [],
    amazonResposta: [null, [Validators.maxLength(255)]],
    referenceId: [],
    created: [null, [Validators.required]],
    updated: [],
    planoRecursoId: [],
  });

  constructor(
    protected notificacaoEnviadaService: NotificacaoEnviadaService,
    protected planoRecursoService: PlanoRecursoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notificacaoEnviada }) => {
      if (!notificacaoEnviada.id) {
        const today = moment().startOf('day');
        notificacaoEnviada.amazonDateLog = today;
        notificacaoEnviada.created = today;
        notificacaoEnviada.updated = today;
      }

      this.updateForm(notificacaoEnviada);

      this.planoRecursoService.query().subscribe((res: HttpResponse<IPlanoRecurso[]>) => (this.planorecursos = res.body || []));
    });
  }

  updateForm(notificacaoEnviada: INotificacaoEnviada): void {
    this.editForm.patchValue({
      id: notificacaoEnviada.id,
      name: notificacaoEnviada.name,
      descricao: notificacaoEnviada.descricao,
      destinatarios: notificacaoEnviada.destinatarios,
      tipo: notificacaoEnviada.tipo,
      status: notificacaoEnviada.status,
      assunto: notificacaoEnviada.assunto,
      enviado: notificacaoEnviada.enviado,
      contador: notificacaoEnviada.contador,
      amazonMessageId: notificacaoEnviada.amazonMessageId,
      amazonDateLog: notificacaoEnviada.amazonDateLog ? notificacaoEnviada.amazonDateLog.format(DATE_TIME_FORMAT) : null,
      priceInUsd: notificacaoEnviada.priceInUsd,
      amazonResposta: notificacaoEnviada.amazonResposta,
      referenceId: notificacaoEnviada.referenceId,
      created: notificacaoEnviada.created ? notificacaoEnviada.created.format(DATE_TIME_FORMAT) : null,
      updated: notificacaoEnviada.updated ? notificacaoEnviada.updated.format(DATE_TIME_FORMAT) : null,
      planoRecursoId: notificacaoEnviada.planoRecursoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notificacaoEnviada = this.createFromForm();
    if (notificacaoEnviada.id !== undefined) {
      this.subscribeToSaveResponse(this.notificacaoEnviadaService.update(notificacaoEnviada));
    } else {
      this.subscribeToSaveResponse(this.notificacaoEnviadaService.create(notificacaoEnviada));
    }
  }

  private createFromForm(): INotificacaoEnviada {
    return {
      ...new NotificacaoEnviada(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      destinatarios: this.editForm.get(['destinatarios'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      status: this.editForm.get(['status'])!.value,
      assunto: this.editForm.get(['assunto'])!.value,
      enviado: this.editForm.get(['enviado'])!.value,
      contador: this.editForm.get(['contador'])!.value,
      amazonMessageId: this.editForm.get(['amazonMessageId'])!.value,
      amazonDateLog: this.editForm.get(['amazonDateLog'])!.value
        ? moment(this.editForm.get(['amazonDateLog'])!.value, DATE_TIME_FORMAT)
        : undefined,
      priceInUsd: this.editForm.get(['priceInUsd'])!.value,
      amazonResposta: this.editForm.get(['amazonResposta'])!.value,
      referenceId: this.editForm.get(['referenceId'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      planoRecursoId: this.editForm.get(['planoRecursoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotificacaoEnviada>>): void {
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

  trackById(index: number, item: IPlanoRecurso): any {
    return item.id;
  }
}

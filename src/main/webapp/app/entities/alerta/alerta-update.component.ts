import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAlerta, Alerta } from 'app/shared/model/alerta.model';
import { AlertaService } from './alerta.service';
import { IPlanoRecurso } from 'app/shared/model/plano-recurso.model';
import { PlanoRecursoService } from 'app/entities/plano-recurso/plano-recurso.service';
import { IAlvo } from 'app/shared/model/alvo.model';
import { AlvoService } from 'app/entities/alvo/alvo.service';
import { IUsuario } from 'app/shared/model/usuario.model';
import { UsuarioService } from 'app/entities/usuario/usuario.service';
import { IAlertaRisco } from 'app/shared/model/alerta-risco.model';
import { AlertaRiscoService } from 'app/entities/alerta-risco/alerta-risco.service';
import { ITempestadeNivel } from 'app/shared/model/tempestade-nivel.model';
import { TempestadeNivelService } from 'app/entities/tempestade-nivel/tempestade-nivel.service';
import { IAlertaTipo } from 'app/shared/model/alerta-tipo.model';
import { AlertaTipoService } from 'app/entities/alerta-tipo/alerta-tipo.service';

type SelectableEntity = IPlanoRecurso | IAlvo | IUsuario | IAlertaRisco | ITempestadeNivel | IAlertaTipo;

@Component({
  selector: 'jhi-alerta-update',
  templateUrl: './alerta-update.component.html',
})
export class AlertaUpdateComponent implements OnInit {
  isSaving = false;
  planorecursos: IPlanoRecurso[] = [];
  alvos: IAlvo[] = [];
  usuarios: IUsuario[] = [];
  alertariscos: IAlertaRisco[] = [];
  tempestadenivels: ITempestadeNivel[] = [];
  alertatipos: IAlertaTipo[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    contato: [null, [Validators.maxLength(255)]],
    duracao: [
      null,
      [Validators.minLength(8), Validators.maxLength(8), Validators.pattern('^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$')],
    ],
    automatico: [],
    critico: [],
    observacao: [null, [Validators.maxLength(255)]],
    alertaPaiId: [],
    created: [null, [Validators.required]],
    updated: [],
    planoRecursoId: [],
    alvoId: [],
    operadorUsuarioId: [],
    alertaRiscoId: [],
    tempestadeNivelId: [],
    alertaTipoId: [],
  });

  constructor(
    protected alertaService: AlertaService,
    protected planoRecursoService: PlanoRecursoService,
    protected alvoService: AlvoService,
    protected usuarioService: UsuarioService,
    protected alertaRiscoService: AlertaRiscoService,
    protected tempestadeNivelService: TempestadeNivelService,
    protected alertaTipoService: AlertaTipoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alerta }) => {
      if (!alerta.id) {
        const today = moment().startOf('day');
        alerta.created = today;
        alerta.updated = today;
      }

      this.updateForm(alerta);

      this.planoRecursoService.query().subscribe((res: HttpResponse<IPlanoRecurso[]>) => (this.planorecursos = res.body || []));

      this.alvoService.query().subscribe((res: HttpResponse<IAlvo[]>) => (this.alvos = res.body || []));

      this.usuarioService.query().subscribe((res: HttpResponse<IUsuario[]>) => (this.usuarios = res.body || []));

      this.alertaRiscoService.query().subscribe((res: HttpResponse<IAlertaRisco[]>) => (this.alertariscos = res.body || []));

      this.tempestadeNivelService.query().subscribe((res: HttpResponse<ITempestadeNivel[]>) => (this.tempestadenivels = res.body || []));

      this.alertaTipoService.query().subscribe((res: HttpResponse<IAlertaTipo[]>) => (this.alertatipos = res.body || []));
    });
  }

  updateForm(alerta: IAlerta): void {
    this.editForm.patchValue({
      id: alerta.id,
      nome: alerta.nome,
      contato: alerta.contato,
      duracao: alerta.duracao,
      automatico: alerta.automatico,
      critico: alerta.critico,
      observacao: alerta.observacao,
      alertaPaiId: alerta.alertaPaiId,
      created: alerta.created ? alerta.created.format(DATE_TIME_FORMAT) : null,
      updated: alerta.updated ? alerta.updated.format(DATE_TIME_FORMAT) : null,
      planoRecursoId: alerta.planoRecursoId,
      alvoId: alerta.alvoId,
      operadorUsuarioId: alerta.operadorUsuarioId,
      alertaRiscoId: alerta.alertaRiscoId,
      tempestadeNivelId: alerta.tempestadeNivelId,
      alertaTipoId: alerta.alertaTipoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alerta = this.createFromForm();
    if (alerta.id !== undefined) {
      this.subscribeToSaveResponse(this.alertaService.update(alerta));
    } else {
      this.subscribeToSaveResponse(this.alertaService.create(alerta));
    }
  }

  private createFromForm(): IAlerta {
    return {
      ...new Alerta(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      contato: this.editForm.get(['contato'])!.value,
      duracao: this.editForm.get(['duracao'])!.value,
      automatico: this.editForm.get(['automatico'])!.value,
      critico: this.editForm.get(['critico'])!.value,
      observacao: this.editForm.get(['observacao'])!.value,
      alertaPaiId: this.editForm.get(['alertaPaiId'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      planoRecursoId: this.editForm.get(['planoRecursoId'])!.value,
      alvoId: this.editForm.get(['alvoId'])!.value,
      operadorUsuarioId: this.editForm.get(['operadorUsuarioId'])!.value,
      alertaRiscoId: this.editForm.get(['alertaRiscoId'])!.value,
      tempestadeNivelId: this.editForm.get(['tempestadeNivelId'])!.value,
      alertaTipoId: this.editForm.get(['alertaTipoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlerta>>): void {
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

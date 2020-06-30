import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPlano, Plano } from 'app/shared/model/plano.model';
import { PlanoService } from './plano.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';
import { IPlanoStatus } from 'app/shared/model/plano-status.model';
import { PlanoStatusService } from 'app/entities/plano-status/plano-status.service';

type SelectableEntity = IEmpresa | IPlanoStatus;

@Component({
  selector: 'jhi-plano-update',
  templateUrl: './plano-update.component.html',
})
export class PlanoUpdateComponent implements OnInit {
  isSaving = false;
  empresas: IEmpresa[] = [];
  planostatuses: IPlanoStatus[] = [];
  dtInicioContratoDp: any;
  dataFimContratoDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    horarioPrevisto: [],
    trackingAtivo: [],
    plrAtivo: [],
    codigoWidgetPrevisao: [],
    kmlAlvo: [null, [Validators.maxLength(255)]],
    zoomMin: [],
    dtInicioContrato: [],
    dataFimContrato: [],
    horarioMonitInicio: [
      null,
      [Validators.minLength(8), Validators.maxLength(8), Validators.pattern('^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$')],
    ],
    horarioMonitFinal: [
      null,
      [Validators.minLength(8), Validators.maxLength(8), Validators.pattern('^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$')],
    ],
    blocos: [null, [Validators.maxLength(255)]],
    extent: [null, [Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
    empresaId: [],
    planoStatusId: [],
  });

  constructor(
    protected planoService: PlanoService,
    protected empresaService: EmpresaService,
    protected planoStatusService: PlanoStatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plano }) => {
      if (!plano.id) {
        const today = moment().startOf('day');
        plano.created = today;
        plano.updated = today;
      }

      this.updateForm(plano);

      this.empresaService.query().subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body || []));

      this.planoStatusService.query().subscribe((res: HttpResponse<IPlanoStatus[]>) => (this.planostatuses = res.body || []));
    });
  }

  updateForm(plano: IPlano): void {
    this.editForm.patchValue({
      id: plano.id,
      name: plano.name,
      descricao: plano.descricao,
      horarioPrevisto: plano.horarioPrevisto,
      trackingAtivo: plano.trackingAtivo,
      plrAtivo: plano.plrAtivo,
      codigoWidgetPrevisao: plano.codigoWidgetPrevisao,
      kmlAlvo: plano.kmlAlvo,
      zoomMin: plano.zoomMin,
      dtInicioContrato: plano.dtInicioContrato,
      dataFimContrato: plano.dataFimContrato,
      horarioMonitInicio: plano.horarioMonitInicio,
      horarioMonitFinal: plano.horarioMonitFinal,
      blocos: plano.blocos,
      extent: plano.extent,
      created: plano.created ? plano.created.format(DATE_TIME_FORMAT) : null,
      updated: plano.updated ? plano.updated.format(DATE_TIME_FORMAT) : null,
      empresaId: plano.empresaId,
      planoStatusId: plano.planoStatusId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plano = this.createFromForm();
    if (plano.id !== undefined) {
      this.subscribeToSaveResponse(this.planoService.update(plano));
    } else {
      this.subscribeToSaveResponse(this.planoService.create(plano));
    }
  }

  private createFromForm(): IPlano {
    return {
      ...new Plano(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      horarioPrevisto: this.editForm.get(['horarioPrevisto'])!.value,
      trackingAtivo: this.editForm.get(['trackingAtivo'])!.value,
      plrAtivo: this.editForm.get(['plrAtivo'])!.value,
      codigoWidgetPrevisao: this.editForm.get(['codigoWidgetPrevisao'])!.value,
      kmlAlvo: this.editForm.get(['kmlAlvo'])!.value,
      zoomMin: this.editForm.get(['zoomMin'])!.value,
      dtInicioContrato: this.editForm.get(['dtInicioContrato'])!.value,
      dataFimContrato: this.editForm.get(['dataFimContrato'])!.value,
      horarioMonitInicio: this.editForm.get(['horarioMonitInicio'])!.value,
      horarioMonitFinal: this.editForm.get(['horarioMonitFinal'])!.value,
      blocos: this.editForm.get(['blocos'])!.value,
      extent: this.editForm.get(['extent'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      empresaId: this.editForm.get(['empresaId'])!.value,
      planoStatusId: this.editForm.get(['planoStatusId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlano>>): void {
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

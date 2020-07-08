import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAlertaFerramenta, AlertaFerramenta } from 'app/shared/model/alerta-ferramenta.model';
import { AlertaFerramentaService } from './alerta-ferramenta.service';
import { IAlerta } from 'app/shared/model/alerta.model';
import { AlertaService } from 'app/entities/alerta/alerta.service';
import { IFerramenta } from 'app/shared/model/ferramenta.model';
import { FerramentaService } from 'app/entities/ferramenta/ferramenta.service';

type SelectableEntity = IAlerta | IFerramenta;

@Component({
  selector: 'jhi-alerta-ferramenta-update',
  templateUrl: './alerta-ferramenta-update.component.html',
})
export class AlertaFerramentaUpdateComponent implements OnInit {
  isSaving = false;
  alertas: IAlerta[] = [];
  ferramentas: IFerramenta[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    created: [null, [Validators.required]],
    updated: [],
    alertaId: [],
    ferramentaId: [],
  });

  constructor(
    protected alertaFerramentaService: AlertaFerramentaService,
    protected alertaService: AlertaService,
    protected ferramentaService: FerramentaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alertaFerramenta }) => {
      if (!alertaFerramenta.id) {
        const today = moment().startOf('day');
        alertaFerramenta.created = today;
        alertaFerramenta.updated = today;
      }

      this.updateForm(alertaFerramenta);

      this.alertaService.query().subscribe((res: HttpResponse<IAlerta[]>) => (this.alertas = res.body || []));

      this.ferramentaService.query().subscribe((res: HttpResponse<IFerramenta[]>) => (this.ferramentas = res.body || []));
    });
  }

  updateForm(alertaFerramenta: IAlertaFerramenta): void {
    this.editForm.patchValue({
      id: alertaFerramenta.id,
      nome: alertaFerramenta.nome,
      descricao: alertaFerramenta.descricao,
      created: alertaFerramenta.created ? alertaFerramenta.created.format(DATE_TIME_FORMAT) : null,
      updated: alertaFerramenta.updated ? alertaFerramenta.updated.format(DATE_TIME_FORMAT) : null,
      alertaId: alertaFerramenta.alertaId,
      ferramentaId: alertaFerramenta.ferramentaId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alertaFerramenta = this.createFromForm();
    if (alertaFerramenta.id !== undefined) {
      this.subscribeToSaveResponse(this.alertaFerramentaService.update(alertaFerramenta));
    } else {
      this.subscribeToSaveResponse(this.alertaFerramentaService.create(alertaFerramenta));
    }
  }

  private createFromForm(): IAlertaFerramenta {
    return {
      ...new AlertaFerramenta(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      alertaId: this.editForm.get(['alertaId'])!.value,
      ferramentaId: this.editForm.get(['ferramentaId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlertaFerramenta>>): void {
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

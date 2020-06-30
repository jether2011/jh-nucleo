import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITipoEnvioIntegrador, TipoEnvioIntegrador } from 'app/shared/model/tipo-envio-integrador.model';
import { TipoEnvioIntegradorService } from './tipo-envio-integrador.service';
import { ITipoEnvio } from 'app/shared/model/tipo-envio.model';
import { TipoEnvioService } from 'app/entities/tipo-envio/tipo-envio.service';
import { IIntegrador } from 'app/shared/model/integrador.model';
import { IntegradorService } from 'app/entities/integrador/integrador.service';

type SelectableEntity = ITipoEnvio | IIntegrador;

@Component({
  selector: 'jhi-tipo-envio-integrador-update',
  templateUrl: './tipo-envio-integrador-update.component.html',
})
export class TipoEnvioIntegradorUpdateComponent implements OnInit {
  isSaving = false;
  tipoenvios: ITipoEnvio[] = [];
  integradors: IIntegrador[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    ativo: [],
    created: [null, [Validators.required]],
    updated: [],
    tipoEnvioId: [null, Validators.required],
    integradorId: [null, Validators.required],
  });

  constructor(
    protected tipoEnvioIntegradorService: TipoEnvioIntegradorService,
    protected tipoEnvioService: TipoEnvioService,
    protected integradorService: IntegradorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoEnvioIntegrador }) => {
      if (!tipoEnvioIntegrador.id) {
        const today = moment().startOf('day');
        tipoEnvioIntegrador.created = today;
        tipoEnvioIntegrador.updated = today;
      }

      this.updateForm(tipoEnvioIntegrador);

      this.tipoEnvioService.query().subscribe((res: HttpResponse<ITipoEnvio[]>) => (this.tipoenvios = res.body || []));

      this.integradorService.query().subscribe((res: HttpResponse<IIntegrador[]>) => (this.integradors = res.body || []));
    });
  }

  updateForm(tipoEnvioIntegrador: ITipoEnvioIntegrador): void {
    this.editForm.patchValue({
      id: tipoEnvioIntegrador.id,
      name: tipoEnvioIntegrador.name,
      descricao: tipoEnvioIntegrador.descricao,
      ativo: tipoEnvioIntegrador.ativo,
      created: tipoEnvioIntegrador.created ? tipoEnvioIntegrador.created.format(DATE_TIME_FORMAT) : null,
      updated: tipoEnvioIntegrador.updated ? tipoEnvioIntegrador.updated.format(DATE_TIME_FORMAT) : null,
      tipoEnvioId: tipoEnvioIntegrador.tipoEnvioId,
      integradorId: tipoEnvioIntegrador.integradorId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoEnvioIntegrador = this.createFromForm();
    if (tipoEnvioIntegrador.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoEnvioIntegradorService.update(tipoEnvioIntegrador));
    } else {
      this.subscribeToSaveResponse(this.tipoEnvioIntegradorService.create(tipoEnvioIntegrador));
    }
  }

  private createFromForm(): ITipoEnvioIntegrador {
    return {
      ...new TipoEnvioIntegrador(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      ativo: this.editForm.get(['ativo'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      tipoEnvioId: this.editForm.get(['tipoEnvioId'])!.value,
      integradorId: this.editForm.get(['integradorId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoEnvioIntegrador>>): void {
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

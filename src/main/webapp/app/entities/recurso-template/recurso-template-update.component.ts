import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRecursoTemplate, RecursoTemplate } from 'app/shared/model/recurso-template.model';
import { RecursoTemplateService } from './recurso-template.service';
import { IRecurso } from 'app/shared/model/recurso.model';
import { RecursoService } from 'app/entities/recurso/recurso.service';
import { ITipoEnvio } from 'app/shared/model/tipo-envio.model';
import { TipoEnvioService } from 'app/entities/tipo-envio/tipo-envio.service';
import { IAlertaTipo } from 'app/shared/model/alerta-tipo.model';
import { AlertaTipoService } from 'app/entities/alerta-tipo/alerta-tipo.service';

type SelectableEntity = IRecurso | ITipoEnvio | IAlertaTipo;

@Component({
  selector: 'jhi-recurso-template-update',
  templateUrl: './recurso-template-update.component.html',
})
export class RecursoTemplateUpdateComponent implements OnInit {
  isSaving = false;
  recursos: IRecurso[] = [];
  tipoenvios: ITipoEnvio[] = [];
  alertatipos: IAlertaTipo[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    template: [null, [Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
    recursoId: [],
    tipoEnvioId: [],
    alertaTipoId: [],
  });

  constructor(
    protected recursoTemplateService: RecursoTemplateService,
    protected recursoService: RecursoService,
    protected tipoEnvioService: TipoEnvioService,
    protected alertaTipoService: AlertaTipoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recursoTemplate }) => {
      if (!recursoTemplate.id) {
        const today = moment().startOf('day');
        recursoTemplate.created = today;
        recursoTemplate.updated = today;
      }

      this.updateForm(recursoTemplate);

      this.recursoService.query().subscribe((res: HttpResponse<IRecurso[]>) => (this.recursos = res.body || []));

      this.tipoEnvioService.query().subscribe((res: HttpResponse<ITipoEnvio[]>) => (this.tipoenvios = res.body || []));

      this.alertaTipoService.query().subscribe((res: HttpResponse<IAlertaTipo[]>) => (this.alertatipos = res.body || []));
    });
  }

  updateForm(recursoTemplate: IRecursoTemplate): void {
    this.editForm.patchValue({
      id: recursoTemplate.id,
      name: recursoTemplate.name,
      descricao: recursoTemplate.descricao,
      template: recursoTemplate.template,
      created: recursoTemplate.created ? recursoTemplate.created.format(DATE_TIME_FORMAT) : null,
      updated: recursoTemplate.updated ? recursoTemplate.updated.format(DATE_TIME_FORMAT) : null,
      recursoId: recursoTemplate.recursoId,
      tipoEnvioId: recursoTemplate.tipoEnvioId,
      alertaTipoId: recursoTemplate.alertaTipoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const recursoTemplate = this.createFromForm();
    if (recursoTemplate.id !== undefined) {
      this.subscribeToSaveResponse(this.recursoTemplateService.update(recursoTemplate));
    } else {
      this.subscribeToSaveResponse(this.recursoTemplateService.create(recursoTemplate));
    }
  }

  private createFromForm(): IRecursoTemplate {
    return {
      ...new RecursoTemplate(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      template: this.editForm.get(['template'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      recursoId: this.editForm.get(['recursoId'])!.value,
      tipoEnvioId: this.editForm.get(['tipoEnvioId'])!.value,
      alertaTipoId: this.editForm.get(['alertaTipoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecursoTemplate>>): void {
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

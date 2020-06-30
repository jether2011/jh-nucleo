import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRecurso, Recurso } from 'app/shared/model/recurso.model';
import { RecursoService } from './recurso.service';
import { IRecursoTipo } from 'app/shared/model/recurso-tipo.model';
import { RecursoTipoService } from 'app/entities/recurso-tipo/recurso-tipo.service';
import { IVariavelMeteorologica } from 'app/shared/model/variavel-meteorologica.model';
import { VariavelMeteorologicaService } from 'app/entities/variavel-meteorologica/variavel-meteorologica.service';

type SelectableEntity = IRecursoTipo | IVariavelMeteorologica;

@Component({
  selector: 'jhi-recurso-update',
  templateUrl: './recurso-update.component.html',
})
export class RecursoUpdateComponent implements OnInit {
  isSaving = false;
  recursotipos: IRecursoTipo[] = [];
  variavelmeteorologicas: IVariavelMeteorologica[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    ativo: [],
    created: [null, [Validators.required]],
    updated: [],
    recursoTipoId: [null, Validators.required],
    variavelMeteorologicaId: [],
  });

  constructor(
    protected recursoService: RecursoService,
    protected recursoTipoService: RecursoTipoService,
    protected variavelMeteorologicaService: VariavelMeteorologicaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recurso }) => {
      if (!recurso.id) {
        const today = moment().startOf('day');
        recurso.created = today;
        recurso.updated = today;
      }

      this.updateForm(recurso);

      this.recursoTipoService.query().subscribe((res: HttpResponse<IRecursoTipo[]>) => (this.recursotipos = res.body || []));

      this.variavelMeteorologicaService
        .query()
        .subscribe((res: HttpResponse<IVariavelMeteorologica[]>) => (this.variavelmeteorologicas = res.body || []));
    });
  }

  updateForm(recurso: IRecurso): void {
    this.editForm.patchValue({
      id: recurso.id,
      name: recurso.name,
      descricao: recurso.descricao,
      ativo: recurso.ativo,
      created: recurso.created ? recurso.created.format(DATE_TIME_FORMAT) : null,
      updated: recurso.updated ? recurso.updated.format(DATE_TIME_FORMAT) : null,
      recursoTipoId: recurso.recursoTipoId,
      variavelMeteorologicaId: recurso.variavelMeteorologicaId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const recurso = this.createFromForm();
    if (recurso.id !== undefined) {
      this.subscribeToSaveResponse(this.recursoService.update(recurso));
    } else {
      this.subscribeToSaveResponse(this.recursoService.create(recurso));
    }
  }

  private createFromForm(): IRecurso {
    return {
      ...new Recurso(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      ativo: this.editForm.get(['ativo'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      recursoTipoId: this.editForm.get(['recursoTipoId'])!.value,
      variavelMeteorologicaId: this.editForm.get(['variavelMeteorologicaId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecurso>>): void {
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

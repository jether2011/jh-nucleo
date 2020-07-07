import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPlanoRecurso, PlanoRecurso } from 'app/shared/model/plano-recurso.model';
import { PlanoRecursoService } from './plano-recurso.service';
import { IPlano } from 'app/shared/model/plano.model';
import { PlanoService } from 'app/entities/plano/plano.service';
import { IRecurso } from 'app/shared/model/recurso.model';
import { RecursoService } from 'app/entities/recurso/recurso.service';

type SelectableEntity = IPlano | IRecurso;

@Component({
  selector: 'jhi-plano-recurso-update',
  templateUrl: './plano-recurso-update.component.html',
})
export class PlanoRecursoUpdateComponent implements OnInit {
  isSaving = false;
  planos: IPlano[] = [];
  recursos: IRecurso[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    ativo: [],
    created: [null, [Validators.required]],
    updated: [],
    planoId: [],
    recursoId: [],
  });

  constructor(
    protected planoRecursoService: PlanoRecursoService,
    protected planoService: PlanoService,
    protected recursoService: RecursoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoRecurso }) => {
      if (!planoRecurso.id) {
        const today = moment().startOf('day');
        planoRecurso.created = today;
        planoRecurso.updated = today;
      }

      this.updateForm(planoRecurso);

      this.planoService.query().subscribe((res: HttpResponse<IPlano[]>) => (this.planos = res.body || []));

      this.recursoService.query().subscribe((res: HttpResponse<IRecurso[]>) => (this.recursos = res.body || []));
    });
  }

  updateForm(planoRecurso: IPlanoRecurso): void {
    this.editForm.patchValue({
      id: planoRecurso.id,
      name: planoRecurso.name,
      descricao: planoRecurso.descricao,
      ativo: planoRecurso.ativo,
      created: planoRecurso.created ? planoRecurso.created.format(DATE_TIME_FORMAT) : null,
      updated: planoRecurso.updated ? planoRecurso.updated.format(DATE_TIME_FORMAT) : null,
      planoId: planoRecurso.planoId,
      recursoId: planoRecurso.recursoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const planoRecurso = this.createFromForm();
    if (planoRecurso.id !== undefined) {
      this.subscribeToSaveResponse(this.planoRecursoService.update(planoRecurso));
    } else {
      this.subscribeToSaveResponse(this.planoRecursoService.create(planoRecurso));
    }
  }

  private createFromForm(): IPlanoRecurso {
    return {
      ...new PlanoRecurso(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      ativo: this.editForm.get(['ativo'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      planoId: this.editForm.get(['planoId'])!.value,
      recursoId: this.editForm.get(['recursoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoRecurso>>): void {
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

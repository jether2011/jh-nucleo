import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPlanoRecursoTipoEnvio, PlanoRecursoTipoEnvio } from 'app/shared/model/plano-recurso-tipo-envio.model';
import { PlanoRecursoTipoEnvioService } from './plano-recurso-tipo-envio.service';
import { IPlanoRecurso } from 'app/shared/model/plano-recurso.model';
import { PlanoRecursoService } from 'app/entities/plano-recurso/plano-recurso.service';
import { ITipoEnvio } from 'app/shared/model/tipo-envio.model';
import { TipoEnvioService } from 'app/entities/tipo-envio/tipo-envio.service';

type SelectableEntity = IPlanoRecurso | ITipoEnvio;

@Component({
  selector: 'jhi-plano-recurso-tipo-envio-update',
  templateUrl: './plano-recurso-tipo-envio-update.component.html',
})
export class PlanoRecursoTipoEnvioUpdateComponent implements OnInit {
  isSaving = false;
  planorecursos: IPlanoRecurso[] = [];
  tipoenvios: ITipoEnvio[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    qtd: [],
    created: [null, [Validators.required]],
    updated: [],
    planoRecursoId: [null, Validators.required],
    tipoEnvioId: [null, Validators.required],
  });

  constructor(
    protected planoRecursoTipoEnvioService: PlanoRecursoTipoEnvioService,
    protected planoRecursoService: PlanoRecursoService,
    protected tipoEnvioService: TipoEnvioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoRecursoTipoEnvio }) => {
      if (!planoRecursoTipoEnvio.id) {
        const today = moment().startOf('day');
        planoRecursoTipoEnvio.created = today;
        planoRecursoTipoEnvio.updated = today;
      }

      this.updateForm(planoRecursoTipoEnvio);

      this.planoRecursoService.query().subscribe((res: HttpResponse<IPlanoRecurso[]>) => (this.planorecursos = res.body || []));

      this.tipoEnvioService.query().subscribe((res: HttpResponse<ITipoEnvio[]>) => (this.tipoenvios = res.body || []));
    });
  }

  updateForm(planoRecursoTipoEnvio: IPlanoRecursoTipoEnvio): void {
    this.editForm.patchValue({
      id: planoRecursoTipoEnvio.id,
      name: planoRecursoTipoEnvio.name,
      descricao: planoRecursoTipoEnvio.descricao,
      qtd: planoRecursoTipoEnvio.qtd,
      created: planoRecursoTipoEnvio.created ? planoRecursoTipoEnvio.created.format(DATE_TIME_FORMAT) : null,
      updated: planoRecursoTipoEnvio.updated ? planoRecursoTipoEnvio.updated.format(DATE_TIME_FORMAT) : null,
      planoRecursoId: planoRecursoTipoEnvio.planoRecursoId,
      tipoEnvioId: planoRecursoTipoEnvio.tipoEnvioId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const planoRecursoTipoEnvio = this.createFromForm();
    if (planoRecursoTipoEnvio.id !== undefined) {
      this.subscribeToSaveResponse(this.planoRecursoTipoEnvioService.update(planoRecursoTipoEnvio));
    } else {
      this.subscribeToSaveResponse(this.planoRecursoTipoEnvioService.create(planoRecursoTipoEnvio));
    }
  }

  private createFromForm(): IPlanoRecursoTipoEnvio {
    return {
      ...new PlanoRecursoTipoEnvio(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      qtd: this.editForm.get(['qtd'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      planoRecursoId: this.editForm.get(['planoRecursoId'])!.value,
      tipoEnvioId: this.editForm.get(['tipoEnvioId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoRecursoTipoEnvio>>): void {
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

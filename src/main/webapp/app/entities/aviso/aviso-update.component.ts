import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAviso, Aviso } from 'app/shared/model/aviso.model';
import { AvisoService } from './aviso.service';
import { IAvisoTipo } from 'app/shared/model/aviso-tipo.model';
import { AvisoTipoService } from 'app/entities/aviso-tipo/aviso-tipo.service';

@Component({
  selector: 'jhi-aviso-update',
  templateUrl: './aviso-update.component.html',
})
export class AvisoUpdateComponent implements OnInit {
  isSaving = false;
  avisotipos: IAvisoTipo[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    enviado: [],
    created: [null, [Validators.required]],
    updated: [],
    avisoTipoId: [],
  });

  constructor(
    protected avisoService: AvisoService,
    protected avisoTipoService: AvisoTipoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aviso }) => {
      if (!aviso.id) {
        const today = moment().startOf('day');
        aviso.created = today;
        aviso.updated = today;
      }

      this.updateForm(aviso);

      this.avisoTipoService.query().subscribe((res: HttpResponse<IAvisoTipo[]>) => (this.avisotipos = res.body || []));
    });
  }

  updateForm(aviso: IAviso): void {
    this.editForm.patchValue({
      id: aviso.id,
      nome: aviso.nome,
      descricao: aviso.descricao,
      enviado: aviso.enviado,
      created: aviso.created ? aviso.created.format(DATE_TIME_FORMAT) : null,
      updated: aviso.updated ? aviso.updated.format(DATE_TIME_FORMAT) : null,
      avisoTipoId: aviso.avisoTipoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const aviso = this.createFromForm();
    if (aviso.id !== undefined) {
      this.subscribeToSaveResponse(this.avisoService.update(aviso));
    } else {
      this.subscribeToSaveResponse(this.avisoService.create(aviso));
    }
  }

  private createFromForm(): IAviso {
    return {
      ...new Aviso(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      enviado: this.editForm.get(['enviado'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      avisoTipoId: this.editForm.get(['avisoTipoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAviso>>): void {
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

  trackById(index: number, item: IAvisoTipo): any {
    return item.id;
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAlvoBloqueio, AlvoBloqueio } from 'app/shared/model/alvo-bloqueio.model';
import { AlvoBloqueioService } from './alvo-bloqueio.service';
import { IAlvo } from 'app/shared/model/alvo.model';
import { AlvoService } from 'app/entities/alvo/alvo.service';

@Component({
  selector: 'jhi-alvo-bloqueio-update',
  templateUrl: './alvo-bloqueio-update.component.html',
})
export class AlvoBloqueioUpdateComponent implements OnInit {
  isSaving = false;
  alvos: IAlvo[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    created: [null, [Validators.required]],
    updated: [],
    alvoId: [],
  });

  constructor(
    protected alvoBloqueioService: AlvoBloqueioService,
    protected alvoService: AlvoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alvoBloqueio }) => {
      if (!alvoBloqueio.id) {
        const today = moment().startOf('day');
        alvoBloqueio.created = today;
        alvoBloqueio.updated = today;
      }

      this.updateForm(alvoBloqueio);

      this.alvoService.query().subscribe((res: HttpResponse<IAlvo[]>) => (this.alvos = res.body || []));
    });
  }

  updateForm(alvoBloqueio: IAlvoBloqueio): void {
    this.editForm.patchValue({
      id: alvoBloqueio.id,
      nome: alvoBloqueio.nome,
      descricao: alvoBloqueio.descricao,
      created: alvoBloqueio.created ? alvoBloqueio.created.format(DATE_TIME_FORMAT) : null,
      updated: alvoBloqueio.updated ? alvoBloqueio.updated.format(DATE_TIME_FORMAT) : null,
      alvoId: alvoBloqueio.alvoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alvoBloqueio = this.createFromForm();
    if (alvoBloqueio.id !== undefined) {
      this.subscribeToSaveResponse(this.alvoBloqueioService.update(alvoBloqueio));
    } else {
      this.subscribeToSaveResponse(this.alvoBloqueioService.create(alvoBloqueio));
    }
  }

  private createFromForm(): IAlvoBloqueio {
    return {
      ...new AlvoBloqueio(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      alvoId: this.editForm.get(['alvoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlvoBloqueio>>): void {
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

  trackById(index: number, item: IAlvo): any {
    return item.id;
  }
}

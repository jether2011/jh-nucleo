import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPlanoRede, PlanoRede } from 'app/shared/model/plano-rede.model';
import { PlanoRedeService } from './plano-rede.service';
import { IPlano } from 'app/shared/model/plano.model';
import { PlanoService } from 'app/entities/plano/plano.service';
import { IRede } from 'app/shared/model/rede.model';
import { RedeService } from 'app/entities/rede/rede.service';

type SelectableEntity = IPlano | IRede;

@Component({
  selector: 'jhi-plano-rede-update',
  templateUrl: './plano-rede-update.component.html',
})
export class PlanoRedeUpdateComponent implements OnInit {
  isSaving = false;
  planos: IPlano[] = [];
  redes: IRede[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
    planoId: [],
    redeId: [],
  });

  constructor(
    protected planoRedeService: PlanoRedeService,
    protected planoService: PlanoService,
    protected redeService: RedeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoRede }) => {
      if (!planoRede.id) {
        const today = moment().startOf('day');
        planoRede.created = today;
        planoRede.updated = today;
      }

      this.updateForm(planoRede);

      this.planoService.query().subscribe((res: HttpResponse<IPlano[]>) => (this.planos = res.body || []));

      this.redeService.query().subscribe((res: HttpResponse<IRede[]>) => (this.redes = res.body || []));
    });
  }

  updateForm(planoRede: IPlanoRede): void {
    this.editForm.patchValue({
      id: planoRede.id,
      name: planoRede.name,
      descricao: planoRede.descricao,
      created: planoRede.created ? planoRede.created.format(DATE_TIME_FORMAT) : null,
      updated: planoRede.updated ? planoRede.updated.format(DATE_TIME_FORMAT) : null,
      planoId: planoRede.planoId,
      redeId: planoRede.redeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const planoRede = this.createFromForm();
    if (planoRede.id !== undefined) {
      this.subscribeToSaveResponse(this.planoRedeService.update(planoRede));
    } else {
      this.subscribeToSaveResponse(this.planoRedeService.create(planoRede));
    }
  }

  private createFromForm(): IPlanoRede {
    return {
      ...new PlanoRede(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      planoId: this.editForm.get(['planoId'])!.value,
      redeId: this.editForm.get(['redeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoRede>>): void {
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

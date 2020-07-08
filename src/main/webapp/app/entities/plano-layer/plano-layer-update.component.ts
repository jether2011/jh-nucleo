import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPlanoLayer, PlanoLayer } from 'app/shared/model/plano-layer.model';
import { PlanoLayerService } from './plano-layer.service';
import { IPlano } from 'app/shared/model/plano.model';
import { PlanoService } from 'app/entities/plano/plano.service';
import { ILayer } from 'app/shared/model/layer.model';
import { LayerService } from 'app/entities/layer/layer.service';
import { IAlvo } from 'app/shared/model/alvo.model';
import { AlvoService } from 'app/entities/alvo/alvo.service';

type SelectableEntity = IPlano | ILayer | IAlvo;

@Component({
  selector: 'jhi-plano-layer-update',
  templateUrl: './plano-layer-update.component.html',
})
export class PlanoLayerUpdateComponent implements OnInit {
  isSaving = false;
  planos: IPlano[] = [];
  layers: ILayer[] = [];
  alvos: IAlvo[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
    planoId: [],
    layerId: [],
    alvoId: [],
  });

  constructor(
    protected planoLayerService: PlanoLayerService,
    protected planoService: PlanoService,
    protected layerService: LayerService,
    protected alvoService: AlvoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoLayer }) => {
      if (!planoLayer.id) {
        const today = moment().startOf('day');
        planoLayer.created = today;
        planoLayer.updated = today;
      }

      this.updateForm(planoLayer);

      this.planoService.query().subscribe((res: HttpResponse<IPlano[]>) => (this.planos = res.body || []));

      this.layerService.query().subscribe((res: HttpResponse<ILayer[]>) => (this.layers = res.body || []));

      this.alvoService.query().subscribe((res: HttpResponse<IAlvo[]>) => (this.alvos = res.body || []));
    });
  }

  updateForm(planoLayer: IPlanoLayer): void {
    this.editForm.patchValue({
      id: planoLayer.id,
      name: planoLayer.name,
      descricao: planoLayer.descricao,
      created: planoLayer.created ? planoLayer.created.format(DATE_TIME_FORMAT) : null,
      updated: planoLayer.updated ? planoLayer.updated.format(DATE_TIME_FORMAT) : null,
      planoId: planoLayer.planoId,
      layerId: planoLayer.layerId,
      alvoId: planoLayer.alvoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const planoLayer = this.createFromForm();
    if (planoLayer.id !== undefined) {
      this.subscribeToSaveResponse(this.planoLayerService.update(planoLayer));
    } else {
      this.subscribeToSaveResponse(this.planoLayerService.create(planoLayer));
    }
  }

  private createFromForm(): IPlanoLayer {
    return {
      ...new PlanoLayer(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      planoId: this.editForm.get(['planoId'])!.value,
      layerId: this.editForm.get(['layerId'])!.value,
      alvoId: this.editForm.get(['alvoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoLayer>>): void {
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

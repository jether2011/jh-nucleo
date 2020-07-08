import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILayer, Layer } from 'app/shared/model/layer.model';
import { LayerService } from './layer.service';

@Component({
  selector: 'jhi-layer-update',
  templateUrl: './layer-update.component.html',
})
export class LayerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    description: [null, [Validators.required, Validators.maxLength(255)]],
    mapHost: [null, [Validators.required]],
    layerType: [null, [Validators.required]],
    title: [null, [Validators.required]],
    attribution: [],
    workspace: [null, [Validators.required]],
    opacity: [null, [Validators.required]],
    baselayer: [null, [Validators.required]],
    tiled: [null, [Validators.required]],
    gwcActived: [null, [Validators.required]],
    active: [null, [Validators.required]],
    enabled: [null, [Validators.required]],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected layerService: LayerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ layer }) => {
      if (!layer.id) {
        const today = moment().startOf('day');
        layer.created = today;
        layer.updated = today;
      }

      this.updateForm(layer);
    });
  }

  updateForm(layer: ILayer): void {
    this.editForm.patchValue({
      id: layer.id,
      name: layer.name,
      description: layer.description,
      mapHost: layer.mapHost,
      layerType: layer.layerType,
      title: layer.title,
      attribution: layer.attribution,
      workspace: layer.workspace,
      opacity: layer.opacity,
      baselayer: layer.baselayer,
      tiled: layer.tiled,
      gwcActived: layer.gwcActived,
      active: layer.active,
      enabled: layer.enabled,
      created: layer.created ? layer.created.format(DATE_TIME_FORMAT) : null,
      updated: layer.updated ? layer.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const layer = this.createFromForm();
    if (layer.id !== undefined) {
      this.subscribeToSaveResponse(this.layerService.update(layer));
    } else {
      this.subscribeToSaveResponse(this.layerService.create(layer));
    }
  }

  private createFromForm(): ILayer {
    return {
      ...new Layer(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      mapHost: this.editForm.get(['mapHost'])!.value,
      layerType: this.editForm.get(['layerType'])!.value,
      title: this.editForm.get(['title'])!.value,
      attribution: this.editForm.get(['attribution'])!.value,
      workspace: this.editForm.get(['workspace'])!.value,
      opacity: this.editForm.get(['opacity'])!.value,
      baselayer: this.editForm.get(['baselayer'])!.value,
      tiled: this.editForm.get(['tiled'])!.value,
      gwcActived: this.editForm.get(['gwcActived'])!.value,
      active: this.editForm.get(['active'])!.value,
      enabled: this.editForm.get(['enabled'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILayer>>): void {
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
}

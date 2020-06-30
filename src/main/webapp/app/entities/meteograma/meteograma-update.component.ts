import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMeteograma, Meteograma } from 'app/shared/model/meteograma.model';
import { MeteogramaService } from './meteograma.service';
import { IPlano } from 'app/shared/model/plano.model';
import { PlanoService } from 'app/entities/plano/plano.service';

@Component({
  selector: 'jhi-meteograma-update',
  templateUrl: './meteograma-update.component.html',
})
export class MeteogramaUpdateComponent implements OnInit {
  isSaving = false;
  planos: IPlano[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    arquivo: [null, [Validators.maxLength(255)]],
    folder: [null, [Validators.maxLength(255)]],
    tipoarquivo: [null, [Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
    planoId: [null, Validators.required],
  });

  constructor(
    protected meteogramaService: MeteogramaService,
    protected planoService: PlanoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ meteograma }) => {
      if (!meteograma.id) {
        const today = moment().startOf('day');
        meteograma.created = today;
        meteograma.updated = today;
      }

      this.updateForm(meteograma);

      this.planoService.query().subscribe((res: HttpResponse<IPlano[]>) => (this.planos = res.body || []));
    });
  }

  updateForm(meteograma: IMeteograma): void {
    this.editForm.patchValue({
      id: meteograma.id,
      name: meteograma.name,
      descricao: meteograma.descricao,
      arquivo: meteograma.arquivo,
      folder: meteograma.folder,
      tipoarquivo: meteograma.tipoarquivo,
      created: meteograma.created ? meteograma.created.format(DATE_TIME_FORMAT) : null,
      updated: meteograma.updated ? meteograma.updated.format(DATE_TIME_FORMAT) : null,
      planoId: meteograma.planoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const meteograma = this.createFromForm();
    if (meteograma.id !== undefined) {
      this.subscribeToSaveResponse(this.meteogramaService.update(meteograma));
    } else {
      this.subscribeToSaveResponse(this.meteogramaService.create(meteograma));
    }
  }

  private createFromForm(): IMeteograma {
    return {
      ...new Meteograma(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      arquivo: this.editForm.get(['arquivo'])!.value,
      folder: this.editForm.get(['folder'])!.value,
      tipoarquivo: this.editForm.get(['tipoarquivo'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      planoId: this.editForm.get(['planoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMeteograma>>): void {
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

  trackById(index: number, item: IPlano): any {
    return item.id;
  }
}

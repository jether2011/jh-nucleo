import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUmidadeAr, UmidadeAr } from 'app/shared/model/umidade-ar.model';
import { UmidadeArService } from './umidade-ar.service';

@Component({
  selector: 'jhi-umidade-ar-update',
  templateUrl: './umidade-ar-update.component.html',
})
export class UmidadeArUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected umidadeArService: UmidadeArService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ umidadeAr }) => {
      if (!umidadeAr.id) {
        const today = moment().startOf('day');
        umidadeAr.created = today;
        umidadeAr.updated = today;
      }

      this.updateForm(umidadeAr);
    });
  }

  updateForm(umidadeAr: IUmidadeAr): void {
    this.editForm.patchValue({
      id: umidadeAr.id,
      name: umidadeAr.name,
      descricao: umidadeAr.descricao,
      created: umidadeAr.created ? umidadeAr.created.format(DATE_TIME_FORMAT) : null,
      updated: umidadeAr.updated ? umidadeAr.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const umidadeAr = this.createFromForm();
    if (umidadeAr.id !== undefined) {
      this.subscribeToSaveResponse(this.umidadeArService.update(umidadeAr));
    } else {
      this.subscribeToSaveResponse(this.umidadeArService.create(umidadeAr));
    }
  }

  private createFromForm(): IUmidadeAr {
    return {
      ...new UmidadeAr(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUmidadeAr>>): void {
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

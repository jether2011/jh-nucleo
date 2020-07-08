import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRede, Rede } from 'app/shared/model/rede.model';
import { RedeService } from './rede.service';

@Component({
  selector: 'jhi-rede-update',
  templateUrl: './rede-update.component.html',
})
export class RedeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected redeService: RedeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rede }) => {
      if (!rede.id) {
        const today = moment().startOf('day');
        rede.created = today;
        rede.updated = today;
      }

      this.updateForm(rede);
    });
  }

  updateForm(rede: IRede): void {
    this.editForm.patchValue({
      id: rede.id,
      name: rede.name,
      descricao: rede.descricao,
      created: rede.created ? rede.created.format(DATE_TIME_FORMAT) : null,
      updated: rede.updated ? rede.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rede = this.createFromForm();
    if (rede.id !== undefined) {
      this.subscribeToSaveResponse(this.redeService.update(rede));
    } else {
      this.subscribeToSaveResponse(this.redeService.create(rede));
    }
  }

  private createFromForm(): IRede {
    return {
      ...new Rede(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRede>>): void {
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

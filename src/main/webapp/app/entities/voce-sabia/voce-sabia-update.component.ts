import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVoceSabia, VoceSabia } from 'app/shared/model/voce-sabia.model';
import { VoceSabiaService } from './voce-sabia.service';

@Component({
  selector: 'jhi-voce-sabia-update',
  templateUrl: './voce-sabia-update.component.html',
})
export class VoceSabiaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    titulo: [null, [Validators.maxLength(255)]],
    texto: [null, [Validators.maxLength(255)]],
    imagem: [null, [Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected voceSabiaService: VoceSabiaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voceSabia }) => {
      if (!voceSabia.id) {
        const today = moment().startOf('day');
        voceSabia.created = today;
        voceSabia.updated = today;
      }

      this.updateForm(voceSabia);
    });
  }

  updateForm(voceSabia: IVoceSabia): void {
    this.editForm.patchValue({
      id: voceSabia.id,
      name: voceSabia.name,
      descricao: voceSabia.descricao,
      titulo: voceSabia.titulo,
      texto: voceSabia.texto,
      imagem: voceSabia.imagem,
      created: voceSabia.created ? voceSabia.created.format(DATE_TIME_FORMAT) : null,
      updated: voceSabia.updated ? voceSabia.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const voceSabia = this.createFromForm();
    if (voceSabia.id !== undefined) {
      this.subscribeToSaveResponse(this.voceSabiaService.update(voceSabia));
    } else {
      this.subscribeToSaveResponse(this.voceSabiaService.create(voceSabia));
    }
  }

  private createFromForm(): IVoceSabia {
    return {
      ...new VoceSabia(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      texto: this.editForm.get(['texto'])!.value,
      imagem: this.editForm.get(['imagem'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoceSabia>>): void {
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

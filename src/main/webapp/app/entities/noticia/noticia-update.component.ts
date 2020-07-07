import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { INoticia, Noticia } from 'app/shared/model/noticia.model';
import { NoticiaService } from './noticia.service';

@Component({
  selector: 'jhi-noticia-update',
  templateUrl: './noticia-update.component.html',
})
export class NoticiaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    texto: [null, [Validators.required, Validators.maxLength(255)]],
    enviado: [],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected noticiaService: NoticiaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ noticia }) => {
      if (!noticia.id) {
        const today = moment().startOf('day');
        noticia.created = today;
        noticia.updated = today;
      }

      this.updateForm(noticia);
    });
  }

  updateForm(noticia: INoticia): void {
    this.editForm.patchValue({
      id: noticia.id,
      name: noticia.name,
      texto: noticia.texto,
      enviado: noticia.enviado,
      created: noticia.created ? noticia.created.format(DATE_TIME_FORMAT) : null,
      updated: noticia.updated ? noticia.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const noticia = this.createFromForm();
    if (noticia.id !== undefined) {
      this.subscribeToSaveResponse(this.noticiaService.update(noticia));
    } else {
      this.subscribeToSaveResponse(this.noticiaService.create(noticia));
    }
  }

  private createFromForm(): INoticia {
    return {
      ...new Noticia(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      texto: this.editForm.get(['texto'])!.value,
      enviado: this.editForm.get(['enviado'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INoticia>>): void {
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

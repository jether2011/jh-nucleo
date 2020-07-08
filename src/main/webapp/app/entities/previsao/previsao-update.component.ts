import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPrevisao, Previsao } from 'app/shared/model/previsao.model';
import { PrevisaoService } from './previsao.service';

@Component({
  selector: 'jhi-previsao-update',
  templateUrl: './previsao-update.component.html',
})
export class PrevisaoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    textoNorte: [null, [Validators.maxLength(255)]],
    textoNorteImagem: [null, [Validators.maxLength(255)]],
    textoNordeste: [null, [Validators.maxLength(255)]],
    textoNordesteImagem: [null, [Validators.maxLength(255)]],
    textoSul: [null, [Validators.maxLength(255)]],
    textoSulImagem: [null, [Validators.maxLength(255)]],
    textoSudeste: [null, [Validators.maxLength(255)]],
    textoSudesteImagem: [null, [Validators.maxLength(255)]],
    textoCentroOeste: [null, [Validators.maxLength(255)]],
    textoCentroOesteImagem: [null, [Validators.maxLength(255)]],
    enviado: [],
    textoBrasil: [null, [Validators.maxLength(255)]],
    textoBrasilImagem: [null, [Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected previsaoService: PrevisaoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ previsao }) => {
      if (!previsao.id) {
        const today = moment().startOf('day');
        previsao.created = today;
        previsao.updated = today;
      }

      this.updateForm(previsao);
    });
  }

  updateForm(previsao: IPrevisao): void {
    this.editForm.patchValue({
      id: previsao.id,
      name: previsao.name,
      descricao: previsao.descricao,
      textoNorte: previsao.textoNorte,
      textoNorteImagem: previsao.textoNorteImagem,
      textoNordeste: previsao.textoNordeste,
      textoNordesteImagem: previsao.textoNordesteImagem,
      textoSul: previsao.textoSul,
      textoSulImagem: previsao.textoSulImagem,
      textoSudeste: previsao.textoSudeste,
      textoSudesteImagem: previsao.textoSudesteImagem,
      textoCentroOeste: previsao.textoCentroOeste,
      textoCentroOesteImagem: previsao.textoCentroOesteImagem,
      enviado: previsao.enviado,
      textoBrasil: previsao.textoBrasil,
      textoBrasilImagem: previsao.textoBrasilImagem,
      created: previsao.created ? previsao.created.format(DATE_TIME_FORMAT) : null,
      updated: previsao.updated ? previsao.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const previsao = this.createFromForm();
    if (previsao.id !== undefined) {
      this.subscribeToSaveResponse(this.previsaoService.update(previsao));
    } else {
      this.subscribeToSaveResponse(this.previsaoService.create(previsao));
    }
  }

  private createFromForm(): IPrevisao {
    return {
      ...new Previsao(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      textoNorte: this.editForm.get(['textoNorte'])!.value,
      textoNorteImagem: this.editForm.get(['textoNorteImagem'])!.value,
      textoNordeste: this.editForm.get(['textoNordeste'])!.value,
      textoNordesteImagem: this.editForm.get(['textoNordesteImagem'])!.value,
      textoSul: this.editForm.get(['textoSul'])!.value,
      textoSulImagem: this.editForm.get(['textoSulImagem'])!.value,
      textoSudeste: this.editForm.get(['textoSudeste'])!.value,
      textoSudesteImagem: this.editForm.get(['textoSudesteImagem'])!.value,
      textoCentroOeste: this.editForm.get(['textoCentroOeste'])!.value,
      textoCentroOesteImagem: this.editForm.get(['textoCentroOesteImagem'])!.value,
      enviado: this.editForm.get(['enviado'])!.value,
      textoBrasil: this.editForm.get(['textoBrasil'])!.value,
      textoBrasilImagem: this.editForm.get(['textoBrasilImagem'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrevisao>>): void {
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

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInformativo, Informativo } from 'app/shared/model/informativo.model';
import { InformativoService } from './informativo.service';
import { IPlanoRecurso } from 'app/shared/model/plano-recurso.model';
import { PlanoRecursoService } from 'app/entities/plano-recurso/plano-recurso.service';

@Component({
  selector: 'jhi-informativo-update',
  templateUrl: './informativo-update.component.html',
})
export class InformativoUpdateComponent implements OnInit {
  isSaving = false;
  planorecursos: IPlanoRecurso[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    texto: [null, [Validators.maxLength(255)]],
    qtdEmail: [],
    imagem: [null, [Validators.maxLength(255)]],
    arquivoEml: [null, [Validators.maxLength(255)]],
    assunto: [null, [Validators.maxLength(255)]],
    subAssunto: [null, [Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
    planoRecursoId: [],
  });

  constructor(
    protected informativoService: InformativoService,
    protected planoRecursoService: PlanoRecursoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ informativo }) => {
      if (!informativo.id) {
        const today = moment().startOf('day');
        informativo.created = today;
        informativo.updated = today;
      }

      this.updateForm(informativo);

      this.planoRecursoService.query().subscribe((res: HttpResponse<IPlanoRecurso[]>) => (this.planorecursos = res.body || []));
    });
  }

  updateForm(informativo: IInformativo): void {
    this.editForm.patchValue({
      id: informativo.id,
      nome: informativo.nome,
      descricao: informativo.descricao,
      texto: informativo.texto,
      qtdEmail: informativo.qtdEmail,
      imagem: informativo.imagem,
      arquivoEml: informativo.arquivoEml,
      assunto: informativo.assunto,
      subAssunto: informativo.subAssunto,
      created: informativo.created ? informativo.created.format(DATE_TIME_FORMAT) : null,
      updated: informativo.updated ? informativo.updated.format(DATE_TIME_FORMAT) : null,
      planoRecursoId: informativo.planoRecursoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const informativo = this.createFromForm();
    if (informativo.id !== undefined) {
      this.subscribeToSaveResponse(this.informativoService.update(informativo));
    } else {
      this.subscribeToSaveResponse(this.informativoService.create(informativo));
    }
  }

  private createFromForm(): IInformativo {
    return {
      ...new Informativo(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      texto: this.editForm.get(['texto'])!.value,
      qtdEmail: this.editForm.get(['qtdEmail'])!.value,
      imagem: this.editForm.get(['imagem'])!.value,
      arquivoEml: this.editForm.get(['arquivoEml'])!.value,
      assunto: this.editForm.get(['assunto'])!.value,
      subAssunto: this.editForm.get(['subAssunto'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      planoRecursoId: this.editForm.get(['planoRecursoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInformativo>>): void {
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

  trackById(index: number, item: IPlanoRecurso): any {
    return item.id;
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IConsolidacao, Consolidacao } from 'app/shared/model/consolidacao.model';
import { ConsolidacaoService } from './consolidacao.service';
import { IPlanoRecurso } from 'app/shared/model/plano-recurso.model';
import { PlanoRecursoService } from 'app/entities/plano-recurso/plano-recurso.service';

@Component({
  selector: 'jhi-consolidacao-update',
  templateUrl: './consolidacao-update.component.html',
})
export class ConsolidacaoUpdateComponent implements OnInit {
  isSaving = false;
  planorecursos: IPlanoRecurso[] = [];
  dataDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    data: [null, [Validators.required]],
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
    protected consolidacaoService: ConsolidacaoService,
    protected planoRecursoService: PlanoRecursoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ consolidacao }) => {
      if (!consolidacao.id) {
        const today = moment().startOf('day');
        consolidacao.created = today;
        consolidacao.updated = today;
      }

      this.updateForm(consolidacao);

      this.planoRecursoService.query().subscribe((res: HttpResponse<IPlanoRecurso[]>) => (this.planorecursos = res.body || []));
    });
  }

  updateForm(consolidacao: IConsolidacao): void {
    this.editForm.patchValue({
      id: consolidacao.id,
      nome: consolidacao.nome,
      descricao: consolidacao.descricao,
      data: consolidacao.data,
      texto: consolidacao.texto,
      qtdEmail: consolidacao.qtdEmail,
      imagem: consolidacao.imagem,
      arquivoEml: consolidacao.arquivoEml,
      assunto: consolidacao.assunto,
      subAssunto: consolidacao.subAssunto,
      created: consolidacao.created ? consolidacao.created.format(DATE_TIME_FORMAT) : null,
      updated: consolidacao.updated ? consolidacao.updated.format(DATE_TIME_FORMAT) : null,
      planoRecursoId: consolidacao.planoRecursoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const consolidacao = this.createFromForm();
    if (consolidacao.id !== undefined) {
      this.subscribeToSaveResponse(this.consolidacaoService.update(consolidacao));
    } else {
      this.subscribeToSaveResponse(this.consolidacaoService.create(consolidacao));
    }
  }

  private createFromForm(): IConsolidacao {
    return {
      ...new Consolidacao(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      data: this.editForm.get(['data'])!.value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConsolidacao>>): void {
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

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBoletim, Boletim } from 'app/shared/model/boletim.model';
import { BoletimService } from './boletim.service';
import { IPlanoRecurso } from 'app/shared/model/plano-recurso.model';
import { PlanoRecursoService } from 'app/entities/plano-recurso/plano-recurso.service';

@Component({
  selector: 'jhi-boletim-update',
  templateUrl: './boletim-update.component.html',
})
export class BoletimUpdateComponent implements OnInit {
  isSaving = false;
  planorecursos: IPlanoRecurso[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    texto: [null, [Validators.maxLength(255)]],
    textoSms: [null, [Validators.maxLength(255)]],
    imagem: [null, [Validators.maxLength(255)]],
    assunto: [null, [Validators.maxLength(255)]],
    textoParte2: [null, [Validators.maxLength(255)]],
    textoParte3: [null, [Validators.maxLength(255)]],
    subAssunto: [null, [Validators.maxLength(255)]],
    naoExibirPagEmpresa: [],
    critico: [],
    aprovado: [],
    enviarSms: [],
    enviarEmail: [],
    created: [null, [Validators.required]],
    updated: [],
    planoRecursoId: [],
  });

  constructor(
    protected boletimService: BoletimService,
    protected planoRecursoService: PlanoRecursoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ boletim }) => {
      if (!boletim.id) {
        const today = moment().startOf('day');
        boletim.created = today;
        boletim.updated = today;
      }

      this.updateForm(boletim);

      this.planoRecursoService.query().subscribe((res: HttpResponse<IPlanoRecurso[]>) => (this.planorecursos = res.body || []));
    });
  }

  updateForm(boletim: IBoletim): void {
    this.editForm.patchValue({
      id: boletim.id,
      nome: boletim.nome,
      descricao: boletim.descricao,
      texto: boletim.texto,
      textoSms: boletim.textoSms,
      imagem: boletim.imagem,
      assunto: boletim.assunto,
      textoParte2: boletim.textoParte2,
      textoParte3: boletim.textoParte3,
      subAssunto: boletim.subAssunto,
      naoExibirPagEmpresa: boletim.naoExibirPagEmpresa,
      critico: boletim.critico,
      aprovado: boletim.aprovado,
      enviarSms: boletim.enviarSms,
      enviarEmail: boletim.enviarEmail,
      created: boletim.created ? boletim.created.format(DATE_TIME_FORMAT) : null,
      updated: boletim.updated ? boletim.updated.format(DATE_TIME_FORMAT) : null,
      planoRecursoId: boletim.planoRecursoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const boletim = this.createFromForm();
    if (boletim.id !== undefined) {
      this.subscribeToSaveResponse(this.boletimService.update(boletim));
    } else {
      this.subscribeToSaveResponse(this.boletimService.create(boletim));
    }
  }

  private createFromForm(): IBoletim {
    return {
      ...new Boletim(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      texto: this.editForm.get(['texto'])!.value,
      textoSms: this.editForm.get(['textoSms'])!.value,
      imagem: this.editForm.get(['imagem'])!.value,
      assunto: this.editForm.get(['assunto'])!.value,
      textoParte2: this.editForm.get(['textoParte2'])!.value,
      textoParte3: this.editForm.get(['textoParte3'])!.value,
      subAssunto: this.editForm.get(['subAssunto'])!.value,
      naoExibirPagEmpresa: this.editForm.get(['naoExibirPagEmpresa'])!.value,
      critico: this.editForm.get(['critico'])!.value,
      aprovado: this.editForm.get(['aprovado'])!.value,
      enviarSms: this.editForm.get(['enviarSms'])!.value,
      enviarEmail: this.editForm.get(['enviarEmail'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      planoRecursoId: this.editForm.get(['planoRecursoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBoletim>>): void {
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

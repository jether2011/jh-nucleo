import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAvisoMeteorologico, AvisoMeteorologico } from 'app/shared/model/aviso-meteorologico.model';
import { AvisoMeteorologicoService } from './aviso-meteorologico.service';
import { IPlanoRecurso } from 'app/shared/model/plano-recurso.model';
import { PlanoRecursoService } from 'app/entities/plano-recurso/plano-recurso.service';

@Component({
  selector: 'jhi-aviso-meteorologico-update',
  templateUrl: './aviso-meteorologico-update.component.html',
})
export class AvisoMeteorologicoUpdateComponent implements OnInit {
  isSaving = false;
  planorecursos: IPlanoRecurso[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    assunto: [null, [Validators.maxLength(255)]],
    inicio: [null, [Validators.required]],
    fim: [null, [Validators.required]],
    texto: [null, [Validators.required, Validators.maxLength(255)]],
    imagem: [null, [Validators.required, Validators.maxLength(255)]],
    imagemAssinatura: [null, [Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
    planoRecursoId: [null, Validators.required],
  });

  constructor(
    protected avisoMeteorologicoService: AvisoMeteorologicoService,
    protected planoRecursoService: PlanoRecursoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avisoMeteorologico }) => {
      if (!avisoMeteorologico.id) {
        const today = moment().startOf('day');
        avisoMeteorologico.inicio = today;
        avisoMeteorologico.fim = today;
        avisoMeteorologico.created = today;
        avisoMeteorologico.updated = today;
      }

      this.updateForm(avisoMeteorologico);

      this.planoRecursoService.query().subscribe((res: HttpResponse<IPlanoRecurso[]>) => (this.planorecursos = res.body || []));
    });
  }

  updateForm(avisoMeteorologico: IAvisoMeteorologico): void {
    this.editForm.patchValue({
      id: avisoMeteorologico.id,
      nome: avisoMeteorologico.nome,
      assunto: avisoMeteorologico.assunto,
      inicio: avisoMeteorologico.inicio ? avisoMeteorologico.inicio.format(DATE_TIME_FORMAT) : null,
      fim: avisoMeteorologico.fim ? avisoMeteorologico.fim.format(DATE_TIME_FORMAT) : null,
      texto: avisoMeteorologico.texto,
      imagem: avisoMeteorologico.imagem,
      imagemAssinatura: avisoMeteorologico.imagemAssinatura,
      created: avisoMeteorologico.created ? avisoMeteorologico.created.format(DATE_TIME_FORMAT) : null,
      updated: avisoMeteorologico.updated ? avisoMeteorologico.updated.format(DATE_TIME_FORMAT) : null,
      planoRecursoId: avisoMeteorologico.planoRecursoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const avisoMeteorologico = this.createFromForm();
    if (avisoMeteorologico.id !== undefined) {
      this.subscribeToSaveResponse(this.avisoMeteorologicoService.update(avisoMeteorologico));
    } else {
      this.subscribeToSaveResponse(this.avisoMeteorologicoService.create(avisoMeteorologico));
    }
  }

  private createFromForm(): IAvisoMeteorologico {
    return {
      ...new AvisoMeteorologico(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      assunto: this.editForm.get(['assunto'])!.value,
      inicio: this.editForm.get(['inicio'])!.value ? moment(this.editForm.get(['inicio'])!.value, DATE_TIME_FORMAT) : undefined,
      fim: this.editForm.get(['fim'])!.value ? moment(this.editForm.get(['fim'])!.value, DATE_TIME_FORMAT) : undefined,
      texto: this.editForm.get(['texto'])!.value,
      imagem: this.editForm.get(['imagem'])!.value,
      imagemAssinatura: this.editForm.get(['imagemAssinatura'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      planoRecursoId: this.editForm.get(['planoRecursoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvisoMeteorologico>>): void {
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

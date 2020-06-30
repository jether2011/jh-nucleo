import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IContatoPlanoRecurso, ContatoPlanoRecurso } from 'app/shared/model/contato-plano-recurso.model';
import { ContatoPlanoRecursoService } from './contato-plano-recurso.service';
import { IContato } from 'app/shared/model/contato.model';
import { ContatoService } from 'app/entities/contato/contato.service';
import { IPlanoRecurso } from 'app/shared/model/plano-recurso.model';
import { PlanoRecursoService } from 'app/entities/plano-recurso/plano-recurso.service';

type SelectableEntity = IContato | IPlanoRecurso;

@Component({
  selector: 'jhi-contato-plano-recurso-update',
  templateUrl: './contato-plano-recurso-update.component.html',
})
export class ContatoPlanoRecursoUpdateComponent implements OnInit {
  isSaving = false;
  contatoes: IContato[] = [];
  planorecursos: IPlanoRecurso[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    created: [null, [Validators.required]],
    updated: [],
    contatoId: [null, Validators.required],
    planoRecursoId: [null, Validators.required],
  });

  constructor(
    protected contatoPlanoRecursoService: ContatoPlanoRecursoService,
    protected contatoService: ContatoService,
    protected planoRecursoService: PlanoRecursoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contatoPlanoRecurso }) => {
      if (!contatoPlanoRecurso.id) {
        const today = moment().startOf('day');
        contatoPlanoRecurso.created = today;
        contatoPlanoRecurso.updated = today;
      }

      this.updateForm(contatoPlanoRecurso);

      this.contatoService.query().subscribe((res: HttpResponse<IContato[]>) => (this.contatoes = res.body || []));

      this.planoRecursoService.query().subscribe((res: HttpResponse<IPlanoRecurso[]>) => (this.planorecursos = res.body || []));
    });
  }

  updateForm(contatoPlanoRecurso: IContatoPlanoRecurso): void {
    this.editForm.patchValue({
      id: contatoPlanoRecurso.id,
      nome: contatoPlanoRecurso.nome,
      descricao: contatoPlanoRecurso.descricao,
      created: contatoPlanoRecurso.created ? contatoPlanoRecurso.created.format(DATE_TIME_FORMAT) : null,
      updated: contatoPlanoRecurso.updated ? contatoPlanoRecurso.updated.format(DATE_TIME_FORMAT) : null,
      contatoId: contatoPlanoRecurso.contatoId,
      planoRecursoId: contatoPlanoRecurso.planoRecursoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contatoPlanoRecurso = this.createFromForm();
    if (contatoPlanoRecurso.id !== undefined) {
      this.subscribeToSaveResponse(this.contatoPlanoRecursoService.update(contatoPlanoRecurso));
    } else {
      this.subscribeToSaveResponse(this.contatoPlanoRecursoService.create(contatoPlanoRecurso));
    }
  }

  private createFromForm(): IContatoPlanoRecurso {
    return {
      ...new ContatoPlanoRecurso(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      contatoId: this.editForm.get(['contatoId'])!.value,
      planoRecursoId: this.editForm.get(['planoRecursoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContatoPlanoRecurso>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}

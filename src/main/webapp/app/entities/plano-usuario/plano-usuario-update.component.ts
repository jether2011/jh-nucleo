import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPlanoUsuario, PlanoUsuario } from 'app/shared/model/plano-usuario.model';
import { PlanoUsuarioService } from './plano-usuario.service';
import { IPlano } from 'app/shared/model/plano.model';
import { PlanoService } from 'app/entities/plano/plano.service';
import { IUsuario } from 'app/shared/model/usuario.model';
import { UsuarioService } from 'app/entities/usuario/usuario.service';

type SelectableEntity = IPlano | IUsuario;

@Component({
  selector: 'jhi-plano-usuario-update',
  templateUrl: './plano-usuario-update.component.html',
})
export class PlanoUsuarioUpdateComponent implements OnInit {
  isSaving = false;
  planos: IPlano[] = [];
  usuarios: IUsuario[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
    planoId: [],
    usuarioId: [],
  });

  constructor(
    protected planoUsuarioService: PlanoUsuarioService,
    protected planoService: PlanoService,
    protected usuarioService: UsuarioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoUsuario }) => {
      if (!planoUsuario.id) {
        const today = moment().startOf('day');
        planoUsuario.created = today;
        planoUsuario.updated = today;
      }

      this.updateForm(planoUsuario);

      this.planoService.query().subscribe((res: HttpResponse<IPlano[]>) => (this.planos = res.body || []));

      this.usuarioService.query().subscribe((res: HttpResponse<IUsuario[]>) => (this.usuarios = res.body || []));
    });
  }

  updateForm(planoUsuario: IPlanoUsuario): void {
    this.editForm.patchValue({
      id: planoUsuario.id,
      name: planoUsuario.name,
      descricao: planoUsuario.descricao,
      created: planoUsuario.created ? planoUsuario.created.format(DATE_TIME_FORMAT) : null,
      updated: planoUsuario.updated ? planoUsuario.updated.format(DATE_TIME_FORMAT) : null,
      planoId: planoUsuario.planoId,
      usuarioId: planoUsuario.usuarioId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const planoUsuario = this.createFromForm();
    if (planoUsuario.id !== undefined) {
      this.subscribeToSaveResponse(this.planoUsuarioService.update(planoUsuario));
    } else {
      this.subscribeToSaveResponse(this.planoUsuarioService.create(planoUsuario));
    }
  }

  private createFromForm(): IPlanoUsuario {
    return {
      ...new PlanoUsuario(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      planoId: this.editForm.get(['planoId'])!.value,
      usuarioId: this.editForm.get(['usuarioId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoUsuario>>): void {
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

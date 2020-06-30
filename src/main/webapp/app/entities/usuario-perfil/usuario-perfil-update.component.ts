import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUsuarioPerfil, UsuarioPerfil } from 'app/shared/model/usuario-perfil.model';
import { UsuarioPerfilService } from './usuario-perfil.service';
import { IUsuario } from 'app/shared/model/usuario.model';
import { UsuarioService } from 'app/entities/usuario/usuario.service';
import { IPerfil } from 'app/shared/model/perfil.model';
import { PerfilService } from 'app/entities/perfil/perfil.service';

type SelectableEntity = IUsuario | IPerfil;

@Component({
  selector: 'jhi-usuario-perfil-update',
  templateUrl: './usuario-perfil-update.component.html',
})
export class UsuarioPerfilUpdateComponent implements OnInit {
  isSaving = false;
  usuarios: IUsuario[] = [];
  perfils: IPerfil[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
    usuarioId: [null, Validators.required],
    perfilId: [null, Validators.required],
  });

  constructor(
    protected usuarioPerfilService: UsuarioPerfilService,
    protected usuarioService: UsuarioService,
    protected perfilService: PerfilService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usuarioPerfil }) => {
      if (!usuarioPerfil.id) {
        const today = moment().startOf('day');
        usuarioPerfil.created = today;
        usuarioPerfil.updated = today;
      }

      this.updateForm(usuarioPerfil);

      this.usuarioService.query().subscribe((res: HttpResponse<IUsuario[]>) => (this.usuarios = res.body || []));

      this.perfilService.query().subscribe((res: HttpResponse<IPerfil[]>) => (this.perfils = res.body || []));
    });
  }

  updateForm(usuarioPerfil: IUsuarioPerfil): void {
    this.editForm.patchValue({
      id: usuarioPerfil.id,
      name: usuarioPerfil.name,
      descricao: usuarioPerfil.descricao,
      created: usuarioPerfil.created ? usuarioPerfil.created.format(DATE_TIME_FORMAT) : null,
      updated: usuarioPerfil.updated ? usuarioPerfil.updated.format(DATE_TIME_FORMAT) : null,
      usuarioId: usuarioPerfil.usuarioId,
      perfilId: usuarioPerfil.perfilId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const usuarioPerfil = this.createFromForm();
    if (usuarioPerfil.id !== undefined) {
      this.subscribeToSaveResponse(this.usuarioPerfilService.update(usuarioPerfil));
    } else {
      this.subscribeToSaveResponse(this.usuarioPerfilService.create(usuarioPerfil));
    }
  }

  private createFromForm(): IUsuarioPerfil {
    return {
      ...new UsuarioPerfil(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      usuarioId: this.editForm.get(['usuarioId'])!.value,
      perfilId: this.editForm.get(['perfilId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsuarioPerfil>>): void {
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

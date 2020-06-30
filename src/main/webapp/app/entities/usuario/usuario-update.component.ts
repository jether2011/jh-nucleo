import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUsuario, Usuario } from 'app/shared/model/usuario.model';
import { UsuarioService } from './usuario.service';

@Component({
  selector: 'jhi-usuario-update',
  templateUrl: './usuario-update.component.html',
})
export class UsuarioUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(60)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    email: [null, [Validators.required, Validators.maxLength(40)]],
    senha: [null, [Validators.required, Validators.maxLength(40)]],
    cnpj: [null, [Validators.maxLength(25)]],
    cpf: [null, [Validators.maxLength(20)]],
    cep: [null, [Validators.maxLength(9)]],
    endereco: [null, [Validators.maxLength(50)]],
    numero: [],
    bairro: [null, [Validators.maxLength(50)]],
    cidade: [null, [Validators.maxLength(50)]],
    estado: [null, [Validators.maxLength(2)]],
    telefone: [null, [Validators.maxLength(20)]],
    fax: [null, [Validators.maxLength(20)]],
    celular: [null, [Validators.maxLength(20)]],
    detalhe: [null, [Validators.maxLength(255)]],
    bloqueado: [],
    complemento: [null, [Validators.maxLength(40)]],
    naoPodeExcluir: [],
    ultimoAcesso: [null, [Validators.required]],
    senhaFirebase: [null, [Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected usuarioService: UsuarioService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usuario }) => {
      if (!usuario.id) {
        const today = moment().startOf('day');
        usuario.ultimoAcesso = today;
        usuario.created = today;
        usuario.updated = today;
      }

      this.updateForm(usuario);
    });
  }

  updateForm(usuario: IUsuario): void {
    this.editForm.patchValue({
      id: usuario.id,
      name: usuario.name,
      descricao: usuario.descricao,
      email: usuario.email,
      senha: usuario.senha,
      cnpj: usuario.cnpj,
      cpf: usuario.cpf,
      cep: usuario.cep,
      endereco: usuario.endereco,
      numero: usuario.numero,
      bairro: usuario.bairro,
      cidade: usuario.cidade,
      estado: usuario.estado,
      telefone: usuario.telefone,
      fax: usuario.fax,
      celular: usuario.celular,
      detalhe: usuario.detalhe,
      bloqueado: usuario.bloqueado,
      complemento: usuario.complemento,
      naoPodeExcluir: usuario.naoPodeExcluir,
      ultimoAcesso: usuario.ultimoAcesso ? usuario.ultimoAcesso.format(DATE_TIME_FORMAT) : null,
      senhaFirebase: usuario.senhaFirebase,
      created: usuario.created ? usuario.created.format(DATE_TIME_FORMAT) : null,
      updated: usuario.updated ? usuario.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const usuario = this.createFromForm();
    if (usuario.id !== undefined) {
      this.subscribeToSaveResponse(this.usuarioService.update(usuario));
    } else {
      this.subscribeToSaveResponse(this.usuarioService.create(usuario));
    }
  }

  private createFromForm(): IUsuario {
    return {
      ...new Usuario(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      email: this.editForm.get(['email'])!.value,
      senha: this.editForm.get(['senha'])!.value,
      cnpj: this.editForm.get(['cnpj'])!.value,
      cpf: this.editForm.get(['cpf'])!.value,
      cep: this.editForm.get(['cep'])!.value,
      endereco: this.editForm.get(['endereco'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      bairro: this.editForm.get(['bairro'])!.value,
      cidade: this.editForm.get(['cidade'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      telefone: this.editForm.get(['telefone'])!.value,
      fax: this.editForm.get(['fax'])!.value,
      celular: this.editForm.get(['celular'])!.value,
      detalhe: this.editForm.get(['detalhe'])!.value,
      bloqueado: this.editForm.get(['bloqueado'])!.value,
      complemento: this.editForm.get(['complemento'])!.value,
      naoPodeExcluir: this.editForm.get(['naoPodeExcluir'])!.value,
      ultimoAcesso: this.editForm.get(['ultimoAcesso'])!.value
        ? moment(this.editForm.get(['ultimoAcesso'])!.value, DATE_TIME_FORMAT)
        : undefined,
      senhaFirebase: this.editForm.get(['senhaFirebase'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsuario>>): void {
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

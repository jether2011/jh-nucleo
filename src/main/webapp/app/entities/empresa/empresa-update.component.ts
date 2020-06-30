import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEmpresa, Empresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from './empresa.service';

@Component({
  selector: 'jhi-empresa-update',
  templateUrl: './empresa-update.component.html',
})
export class EmpresaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    email: [null, [Validators.required, Validators.maxLength(255)]],
    titulo: [null, [Validators.maxLength(255)]],
    nomeReduzido: [null, [Validators.maxLength(255)]],
    logo: [null, [Validators.maxLength(255)]],
    apelido: [null, [Validators.maxLength(255)]],
    observacao: [null, [Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
  });

  constructor(protected empresaService: EmpresaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empresa }) => {
      if (!empresa.id) {
        const today = moment().startOf('day');
        empresa.created = today;
        empresa.updated = today;
      }

      this.updateForm(empresa);
    });
  }

  updateForm(empresa: IEmpresa): void {
    this.editForm.patchValue({
      id: empresa.id,
      nome: empresa.nome,
      descricao: empresa.descricao,
      email: empresa.email,
      titulo: empresa.titulo,
      nomeReduzido: empresa.nomeReduzido,
      logo: empresa.logo,
      apelido: empresa.apelido,
      observacao: empresa.observacao,
      created: empresa.created ? empresa.created.format(DATE_TIME_FORMAT) : null,
      updated: empresa.updated ? empresa.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const empresa = this.createFromForm();
    if (empresa.id !== undefined) {
      this.subscribeToSaveResponse(this.empresaService.update(empresa));
    } else {
      this.subscribeToSaveResponse(this.empresaService.create(empresa));
    }
  }

  private createFromForm(): IEmpresa {
    return {
      ...new Empresa(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      email: this.editForm.get(['email'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      nomeReduzido: this.editForm.get(['nomeReduzido'])!.value,
      logo: this.editForm.get(['logo'])!.value,
      apelido: this.editForm.get(['apelido'])!.value,
      observacao: this.editForm.get(['observacao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpresa>>): void {
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

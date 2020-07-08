import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IContatoTipoEnvio, ContatoTipoEnvio } from 'app/shared/model/contato-tipo-envio.model';
import { ContatoTipoEnvioService } from './contato-tipo-envio.service';
import { IContato } from 'app/shared/model/contato.model';
import { ContatoService } from 'app/entities/contato/contato.service';
import { ITipoEnvio } from 'app/shared/model/tipo-envio.model';
import { TipoEnvioService } from 'app/entities/tipo-envio/tipo-envio.service';

type SelectableEntity = IContato | ITipoEnvio;

@Component({
  selector: 'jhi-contato-tipo-envio-update',
  templateUrl: './contato-tipo-envio-update.component.html',
})
export class ContatoTipoEnvioUpdateComponent implements OnInit {
  isSaving = false;
  contatoes: IContato[] = [];
  tipoenvios: ITipoEnvio[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    created: [null, [Validators.required]],
    updated: [],
    contatoId: [],
    tipoEnvioId: [],
  });

  constructor(
    protected contatoTipoEnvioService: ContatoTipoEnvioService,
    protected contatoService: ContatoService,
    protected tipoEnvioService: TipoEnvioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contatoTipoEnvio }) => {
      if (!contatoTipoEnvio.id) {
        const today = moment().startOf('day');
        contatoTipoEnvio.created = today;
        contatoTipoEnvio.updated = today;
      }

      this.updateForm(contatoTipoEnvio);

      this.contatoService.query().subscribe((res: HttpResponse<IContato[]>) => (this.contatoes = res.body || []));

      this.tipoEnvioService.query().subscribe((res: HttpResponse<ITipoEnvio[]>) => (this.tipoenvios = res.body || []));
    });
  }

  updateForm(contatoTipoEnvio: IContatoTipoEnvio): void {
    this.editForm.patchValue({
      id: contatoTipoEnvio.id,
      nome: contatoTipoEnvio.nome,
      descricao: contatoTipoEnvio.descricao,
      created: contatoTipoEnvio.created ? contatoTipoEnvio.created.format(DATE_TIME_FORMAT) : null,
      updated: contatoTipoEnvio.updated ? contatoTipoEnvio.updated.format(DATE_TIME_FORMAT) : null,
      contatoId: contatoTipoEnvio.contatoId,
      tipoEnvioId: contatoTipoEnvio.tipoEnvioId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contatoTipoEnvio = this.createFromForm();
    if (contatoTipoEnvio.id !== undefined) {
      this.subscribeToSaveResponse(this.contatoTipoEnvioService.update(contatoTipoEnvio));
    } else {
      this.subscribeToSaveResponse(this.contatoTipoEnvioService.create(contatoTipoEnvio));
    }
  }

  private createFromForm(): IContatoTipoEnvio {
    return {
      ...new ContatoTipoEnvio(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      contatoId: this.editForm.get(['contatoId'])!.value,
      tipoEnvioId: this.editForm.get(['tipoEnvioId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContatoTipoEnvio>>): void {
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

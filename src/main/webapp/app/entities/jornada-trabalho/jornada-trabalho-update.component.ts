import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IJornadaTrabalho, JornadaTrabalho } from 'app/shared/model/jornada-trabalho.model';
import { JornadaTrabalhoService } from './jornada-trabalho.service';
import { IPlano } from 'app/shared/model/plano.model';
import { PlanoService } from 'app/entities/plano/plano.service';
import { IDiaSemana } from 'app/shared/model/dia-semana.model';
import { DiaSemanaService } from 'app/entities/dia-semana/dia-semana.service';

type SelectableEntity = IPlano | IDiaSemana;

@Component({
  selector: 'jhi-jornada-trabalho-update',
  templateUrl: './jornada-trabalho-update.component.html',
})
export class JornadaTrabalhoUpdateComponent implements OnInit {
  isSaving = false;
  planos: IPlano[] = [];
  diasemanas: IDiaSemana[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    horainicio: [
      null,
      [Validators.minLength(8), Validators.maxLength(8), Validators.pattern('^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$')],
    ],
    duracao: [
      null,
      [Validators.minLength(8), Validators.maxLength(8), Validators.pattern('^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$')],
    ],
    created: [null, [Validators.required]],
    updated: [],
    planoId: [null, Validators.required],
    diaSemanaId: [null, Validators.required],
  });

  constructor(
    protected jornadaTrabalhoService: JornadaTrabalhoService,
    protected planoService: PlanoService,
    protected diaSemanaService: DiaSemanaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jornadaTrabalho }) => {
      if (!jornadaTrabalho.id) {
        const today = moment().startOf('day');
        jornadaTrabalho.created = today;
        jornadaTrabalho.updated = today;
      }

      this.updateForm(jornadaTrabalho);

      this.planoService.query().subscribe((res: HttpResponse<IPlano[]>) => (this.planos = res.body || []));

      this.diaSemanaService.query().subscribe((res: HttpResponse<IDiaSemana[]>) => (this.diasemanas = res.body || []));
    });
  }

  updateForm(jornadaTrabalho: IJornadaTrabalho): void {
    this.editForm.patchValue({
      id: jornadaTrabalho.id,
      nome: jornadaTrabalho.nome,
      descricao: jornadaTrabalho.descricao,
      horainicio: jornadaTrabalho.horainicio,
      duracao: jornadaTrabalho.duracao,
      created: jornadaTrabalho.created ? jornadaTrabalho.created.format(DATE_TIME_FORMAT) : null,
      updated: jornadaTrabalho.updated ? jornadaTrabalho.updated.format(DATE_TIME_FORMAT) : null,
      planoId: jornadaTrabalho.planoId,
      diaSemanaId: jornadaTrabalho.diaSemanaId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jornadaTrabalho = this.createFromForm();
    if (jornadaTrabalho.id !== undefined) {
      this.subscribeToSaveResponse(this.jornadaTrabalhoService.update(jornadaTrabalho));
    } else {
      this.subscribeToSaveResponse(this.jornadaTrabalhoService.create(jornadaTrabalho));
    }
  }

  private createFromForm(): IJornadaTrabalho {
    return {
      ...new JornadaTrabalho(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      horainicio: this.editForm.get(['horainicio'])!.value,
      duracao: this.editForm.get(['duracao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      planoId: this.editForm.get(['planoId'])!.value,
      diaSemanaId: this.editForm.get(['diaSemanaId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJornadaTrabalho>>): void {
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

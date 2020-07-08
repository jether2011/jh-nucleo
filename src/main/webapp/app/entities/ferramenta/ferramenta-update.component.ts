import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFerramenta, Ferramenta } from 'app/shared/model/ferramenta.model';
import { FerramentaService } from './ferramenta.service';
import { ITipoFerramenta } from 'app/shared/model/tipo-ferramenta.model';
import { TipoFerramentaService } from 'app/entities/tipo-ferramenta/tipo-ferramenta.service';

@Component({
  selector: 'jhi-ferramenta-update',
  templateUrl: './ferramenta-update.component.html',
})
export class FerramentaUpdateComponent implements OnInit {
  isSaving = false;
  tipoferramentas: ITipoFerramenta[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [],
    created: [null, [Validators.required]],
    updated: [],
    tipoFerramentaId: [],
  });

  constructor(
    protected ferramentaService: FerramentaService,
    protected tipoFerramentaService: TipoFerramentaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ferramenta }) => {
      if (!ferramenta.id) {
        const today = moment().startOf('day');
        ferramenta.created = today;
      }

      this.updateForm(ferramenta);

      this.tipoFerramentaService.query().subscribe((res: HttpResponse<ITipoFerramenta[]>) => (this.tipoferramentas = res.body || []));
    });
  }

  updateForm(ferramenta: IFerramenta): void {
    this.editForm.patchValue({
      id: ferramenta.id,
      nome: ferramenta.nome,
      descricao: ferramenta.descricao,
      created: ferramenta.created ? ferramenta.created.format(DATE_TIME_FORMAT) : null,
      updated: ferramenta.updated,
      tipoFerramentaId: ferramenta.tipoFerramentaId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ferramenta = this.createFromForm();
    if (ferramenta.id !== undefined) {
      this.subscribeToSaveResponse(this.ferramentaService.update(ferramenta));
    } else {
      this.subscribeToSaveResponse(this.ferramentaService.create(ferramenta));
    }
  }

  private createFromForm(): IFerramenta {
    return {
      ...new Ferramenta(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value,
      tipoFerramentaId: this.editForm.get(['tipoFerramentaId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFerramenta>>): void {
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

  trackById(index: number, item: ITipoFerramenta): any {
    return item.id;
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITempestadeNivel, TempestadeNivel } from 'app/shared/model/tempestade-nivel.model';
import { TempestadeNivelService } from './tempestade-nivel.service';
import { IIntensidadeChuva } from 'app/shared/model/intensidade-chuva.model';
import { IntensidadeChuvaService } from 'app/entities/intensidade-chuva/intensidade-chuva.service';

@Component({
  selector: 'jhi-tempestade-nivel-update',
  templateUrl: './tempestade-nivel-update.component.html',
})
export class TempestadeNivelUpdateComponent implements OnInit {
  isSaving = false;
  intensidadechuvas: IIntensidadeChuva[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(255)]],
    taxaDeRaios: [null, [Validators.maxLength(255)]],
    ventosVelocidade: [null, [Validators.maxLength(255)]],
    granizo: [null, [Validators.maxLength(255)]],
    potencialDeDanos: [null, [Validators.maxLength(255)]],
    created: [null, [Validators.required]],
    updated: [],
    intensidadeChuvaId: [],
  });

  constructor(
    protected tempestadeNivelService: TempestadeNivelService,
    protected intensidadeChuvaService: IntensidadeChuvaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tempestadeNivel }) => {
      if (!tempestadeNivel.id) {
        const today = moment().startOf('day');
        tempestadeNivel.created = today;
        tempestadeNivel.updated = today;
      }

      this.updateForm(tempestadeNivel);

      this.intensidadeChuvaService.query().subscribe((res: HttpResponse<IIntensidadeChuva[]>) => (this.intensidadechuvas = res.body || []));
    });
  }

  updateForm(tempestadeNivel: ITempestadeNivel): void {
    this.editForm.patchValue({
      id: tempestadeNivel.id,
      name: tempestadeNivel.name,
      descricao: tempestadeNivel.descricao,
      taxaDeRaios: tempestadeNivel.taxaDeRaios,
      ventosVelocidade: tempestadeNivel.ventosVelocidade,
      granizo: tempestadeNivel.granizo,
      potencialDeDanos: tempestadeNivel.potencialDeDanos,
      created: tempestadeNivel.created ? tempestadeNivel.created.format(DATE_TIME_FORMAT) : null,
      updated: tempestadeNivel.updated ? tempestadeNivel.updated.format(DATE_TIME_FORMAT) : null,
      intensidadeChuvaId: tempestadeNivel.intensidadeChuvaId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tempestadeNivel = this.createFromForm();
    if (tempestadeNivel.id !== undefined) {
      this.subscribeToSaveResponse(this.tempestadeNivelService.update(tempestadeNivel));
    } else {
      this.subscribeToSaveResponse(this.tempestadeNivelService.create(tempestadeNivel));
    }
  }

  private createFromForm(): ITempestadeNivel {
    return {
      ...new TempestadeNivel(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      taxaDeRaios: this.editForm.get(['taxaDeRaios'])!.value,
      ventosVelocidade: this.editForm.get(['ventosVelocidade'])!.value,
      granizo: this.editForm.get(['granizo'])!.value,
      potencialDeDanos: this.editForm.get(['potencialDeDanos'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? moment(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      intensidadeChuvaId: this.editForm.get(['intensidadeChuvaId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITempestadeNivel>>): void {
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

  trackById(index: number, item: IIntensidadeChuva): any {
    return item.id;
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInformativo } from 'app/shared/model/informativo.model';

@Component({
  selector: 'jhi-informativo-detail',
  templateUrl: './informativo-detail.component.html',
})
export class InformativoDetailComponent implements OnInit {
  informativo: IInformativo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ informativo }) => (this.informativo = informativo));
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecursoTemplate } from 'app/shared/model/recurso-template.model';

@Component({
  selector: 'jhi-recurso-template-detail',
  templateUrl: './recurso-template-detail.component.html',
})
export class RecursoTemplateDetailComponent implements OnInit {
  recursoTemplate: IRecursoTemplate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recursoTemplate }) => (this.recursoTemplate = recursoTemplate));
  }

  previousState(): void {
    window.history.back();
  }
}

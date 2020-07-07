import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBoletim } from 'app/shared/model/boletim.model';

@Component({
  selector: 'jhi-boletim-detail',
  templateUrl: './boletim-detail.component.html',
})
export class BoletimDetailComponent implements OnInit {
  boletim: IBoletim | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ boletim }) => (this.boletim = boletim));
  }

  previousState(): void {
    window.history.back();
  }
}

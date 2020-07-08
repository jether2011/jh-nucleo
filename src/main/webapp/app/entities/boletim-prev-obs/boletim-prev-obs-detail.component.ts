import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBoletimPrevObs } from 'app/shared/model/boletim-prev-obs.model';

@Component({
  selector: 'jhi-boletim-prev-obs-detail',
  templateUrl: './boletim-prev-obs-detail.component.html',
})
export class BoletimPrevObsDetailComponent implements OnInit {
  boletimPrevObs: IBoletimPrevObs | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ boletimPrevObs }) => (this.boletimPrevObs = boletimPrevObs));
  }

  previousState(): void {
    window.history.back();
  }
}

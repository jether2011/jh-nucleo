import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJornadaTrabalho } from 'app/shared/model/jornada-trabalho.model';

@Component({
  selector: 'jhi-jornada-trabalho-detail',
  templateUrl: './jornada-trabalho-detail.component.html',
})
export class JornadaTrabalhoDetailComponent implements OnInit {
  jornadaTrabalho: IJornadaTrabalho | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jornadaTrabalho }) => (this.jornadaTrabalho = jornadaTrabalho));
  }

  previousState(): void {
    window.history.back();
  }
}

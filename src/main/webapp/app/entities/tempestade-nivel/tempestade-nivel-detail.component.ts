import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITempestadeNivel } from 'app/shared/model/tempestade-nivel.model';

@Component({
  selector: 'jhi-tempestade-nivel-detail',
  templateUrl: './tempestade-nivel-detail.component.html',
})
export class TempestadeNivelDetailComponent implements OnInit {
  tempestadeNivel: ITempestadeNivel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tempestadeNivel }) => (this.tempestadeNivel = tempestadeNivel));
  }

  previousState(): void {
    window.history.back();
  }
}

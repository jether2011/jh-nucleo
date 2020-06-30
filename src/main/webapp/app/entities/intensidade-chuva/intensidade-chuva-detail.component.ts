import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIntensidadeChuva } from 'app/shared/model/intensidade-chuva.model';

@Component({
  selector: 'jhi-intensidade-chuva-detail',
  templateUrl: './intensidade-chuva-detail.component.html',
})
export class IntensidadeChuvaDetailComponent implements OnInit {
  intensidadeChuva: IIntensidadeChuva | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ intensidadeChuva }) => (this.intensidadeChuva = intensidadeChuva));
  }

  previousState(): void {
    window.history.back();
  }
}

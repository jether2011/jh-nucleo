import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUmidadeAr } from 'app/shared/model/umidade-ar.model';

@Component({
  selector: 'jhi-umidade-ar-detail',
  templateUrl: './umidade-ar-detail.component.html',
})
export class UmidadeArDetailComponent implements OnInit {
  umidadeAr: IUmidadeAr | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ umidadeAr }) => (this.umidadeAr = umidadeAr));
  }

  previousState(): void {
    window.history.back();
  }
}

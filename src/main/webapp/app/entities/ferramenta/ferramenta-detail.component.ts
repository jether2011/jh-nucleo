import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFerramenta } from 'app/shared/model/ferramenta.model';

@Component({
  selector: 'jhi-ferramenta-detail',
  templateUrl: './ferramenta-detail.component.html',
})
export class FerramentaDetailComponent implements OnInit {
  ferramenta: IFerramenta | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ferramenta }) => (this.ferramenta = ferramenta));
  }

  previousState(): void {
    window.history.back();
  }
}

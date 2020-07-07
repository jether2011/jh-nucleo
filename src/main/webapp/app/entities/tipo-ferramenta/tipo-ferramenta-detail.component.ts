import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoFerramenta } from 'app/shared/model/tipo-ferramenta.model';

@Component({
  selector: 'jhi-tipo-ferramenta-detail',
  templateUrl: './tipo-ferramenta-detail.component.html',
})
export class TipoFerramentaDetailComponent implements OnInit {
  tipoFerramenta: ITipoFerramenta | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoFerramenta }) => (this.tipoFerramenta = tipoFerramenta));
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlanoRede } from 'app/shared/model/plano-rede.model';

@Component({
  selector: 'jhi-plano-rede-detail',
  templateUrl: './plano-rede-detail.component.html',
})
export class PlanoRedeDetailComponent implements OnInit {
  planoRede: IPlanoRede | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoRede }) => (this.planoRede = planoRede));
  }

  previousState(): void {
    window.history.back();
  }
}

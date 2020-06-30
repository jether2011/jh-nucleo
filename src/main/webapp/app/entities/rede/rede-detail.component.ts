import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRede } from 'app/shared/model/rede.model';

@Component({
  selector: 'jhi-rede-detail',
  templateUrl: './rede-detail.component.html',
})
export class RedeDetailComponent implements OnInit {
  rede: IRede | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rede }) => (this.rede = rede));
  }

  previousState(): void {
    window.history.back();
  }
}

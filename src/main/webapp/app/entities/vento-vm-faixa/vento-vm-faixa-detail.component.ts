import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVentoVmFaixa } from 'app/shared/model/vento-vm-faixa.model';

@Component({
  selector: 'jhi-vento-vm-faixa-detail',
  templateUrl: './vento-vm-faixa-detail.component.html',
})
export class VentoVmFaixaDetailComponent implements OnInit {
  ventoVmFaixa: IVentoVmFaixa | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ventoVmFaixa }) => (this.ventoVmFaixa = ventoVmFaixa));
  }

  previousState(): void {
    window.history.back();
  }
}

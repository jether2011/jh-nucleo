import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVoceSabia } from 'app/shared/model/voce-sabia.model';

@Component({
  selector: 'jhi-voce-sabia-detail',
  templateUrl: './voce-sabia-detail.component.html',
})
export class VoceSabiaDetailComponent implements OnInit {
  voceSabia: IVoceSabia | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voceSabia }) => (this.voceSabia = voceSabia));
  }

  previousState(): void {
    window.history.back();
  }
}

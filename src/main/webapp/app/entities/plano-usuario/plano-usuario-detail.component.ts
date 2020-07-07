import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlanoUsuario } from 'app/shared/model/plano-usuario.model';

@Component({
  selector: 'jhi-plano-usuario-detail',
  templateUrl: './plano-usuario-detail.component.html',
})
export class PlanoUsuarioDetailComponent implements OnInit {
  planoUsuario: IPlanoUsuario | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoUsuario }) => (this.planoUsuario = planoUsuario));
  }

  previousState(): void {
    window.history.back();
  }
}

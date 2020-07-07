import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUsuarioPerfil } from 'app/shared/model/usuario-perfil.model';

@Component({
  selector: 'jhi-usuario-perfil-detail',
  templateUrl: './usuario-perfil-detail.component.html',
})
export class UsuarioPerfilDetailComponent implements OnInit {
  usuarioPerfil: IUsuarioPerfil | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usuarioPerfil }) => (this.usuarioPerfil = usuarioPerfil));
  }

  previousState(): void {
    window.history.back();
  }
}

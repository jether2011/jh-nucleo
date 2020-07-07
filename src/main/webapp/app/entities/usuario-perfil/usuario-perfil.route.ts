import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUsuarioPerfil, UsuarioPerfil } from 'app/shared/model/usuario-perfil.model';
import { UsuarioPerfilService } from './usuario-perfil.service';
import { UsuarioPerfilComponent } from './usuario-perfil.component';
import { UsuarioPerfilDetailComponent } from './usuario-perfil-detail.component';
import { UsuarioPerfilUpdateComponent } from './usuario-perfil-update.component';

@Injectable({ providedIn: 'root' })
export class UsuarioPerfilResolve implements Resolve<IUsuarioPerfil> {
  constructor(private service: UsuarioPerfilService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUsuarioPerfil> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((usuarioPerfil: HttpResponse<UsuarioPerfil>) => {
          if (usuarioPerfil.body) {
            return of(usuarioPerfil.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UsuarioPerfil());
  }
}

export const usuarioPerfilRoute: Routes = [
  {
    path: '',
    component: UsuarioPerfilComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.usuarioPerfil.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UsuarioPerfilDetailComponent,
    resolve: {
      usuarioPerfil: UsuarioPerfilResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.usuarioPerfil.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UsuarioPerfilUpdateComponent,
    resolve: {
      usuarioPerfil: UsuarioPerfilResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.usuarioPerfil.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UsuarioPerfilUpdateComponent,
    resolve: {
      usuarioPerfil: UsuarioPerfilResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.usuarioPerfil.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

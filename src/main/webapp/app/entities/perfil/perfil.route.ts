import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPerfil, Perfil } from 'app/shared/model/perfil.model';
import { PerfilService } from './perfil.service';
import { PerfilComponent } from './perfil.component';
import { PerfilDetailComponent } from './perfil-detail.component';
import { PerfilUpdateComponent } from './perfil-update.component';

@Injectable({ providedIn: 'root' })
export class PerfilResolve implements Resolve<IPerfil> {
  constructor(private service: PerfilService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPerfil> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((perfil: HttpResponse<Perfil>) => {
          if (perfil.body) {
            return of(perfil.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Perfil());
  }
}

export const perfilRoute: Routes = [
  {
    path: '',
    component: PerfilComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.perfil.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PerfilDetailComponent,
    resolve: {
      perfil: PerfilResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.perfil.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PerfilUpdateComponent,
    resolve: {
      perfil: PerfilResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.perfil.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PerfilUpdateComponent,
    resolve: {
      perfil: PerfilResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.perfil.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlanoUsuario, PlanoUsuario } from 'app/shared/model/plano-usuario.model';
import { PlanoUsuarioService } from './plano-usuario.service';
import { PlanoUsuarioComponent } from './plano-usuario.component';
import { PlanoUsuarioDetailComponent } from './plano-usuario-detail.component';
import { PlanoUsuarioUpdateComponent } from './plano-usuario-update.component';

@Injectable({ providedIn: 'root' })
export class PlanoUsuarioResolve implements Resolve<IPlanoUsuario> {
  constructor(private service: PlanoUsuarioService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlanoUsuario> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((planoUsuario: HttpResponse<PlanoUsuario>) => {
          if (planoUsuario.body) {
            return of(planoUsuario.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlanoUsuario());
  }
}

export const planoUsuarioRoute: Routes = [
  {
    path: '',
    component: PlanoUsuarioComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.planoUsuario.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlanoUsuarioDetailComponent,
    resolve: {
      planoUsuario: PlanoUsuarioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.planoUsuario.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlanoUsuarioUpdateComponent,
    resolve: {
      planoUsuario: PlanoUsuarioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.planoUsuario.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlanoUsuarioUpdateComponent,
    resolve: {
      planoUsuario: PlanoUsuarioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.planoUsuario.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

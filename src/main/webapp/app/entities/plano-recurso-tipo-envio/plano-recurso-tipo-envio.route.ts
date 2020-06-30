import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlanoRecursoTipoEnvio, PlanoRecursoTipoEnvio } from 'app/shared/model/plano-recurso-tipo-envio.model';
import { PlanoRecursoTipoEnvioService } from './plano-recurso-tipo-envio.service';
import { PlanoRecursoTipoEnvioComponent } from './plano-recurso-tipo-envio.component';
import { PlanoRecursoTipoEnvioDetailComponent } from './plano-recurso-tipo-envio-detail.component';
import { PlanoRecursoTipoEnvioUpdateComponent } from './plano-recurso-tipo-envio-update.component';

@Injectable({ providedIn: 'root' })
export class PlanoRecursoTipoEnvioResolve implements Resolve<IPlanoRecursoTipoEnvio> {
  constructor(private service: PlanoRecursoTipoEnvioService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlanoRecursoTipoEnvio> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((planoRecursoTipoEnvio: HttpResponse<PlanoRecursoTipoEnvio>) => {
          if (planoRecursoTipoEnvio.body) {
            return of(planoRecursoTipoEnvio.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlanoRecursoTipoEnvio());
  }
}

export const planoRecursoTipoEnvioRoute: Routes = [
  {
    path: '',
    component: PlanoRecursoTipoEnvioComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.planoRecursoTipoEnvio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlanoRecursoTipoEnvioDetailComponent,
    resolve: {
      planoRecursoTipoEnvio: PlanoRecursoTipoEnvioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.planoRecursoTipoEnvio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlanoRecursoTipoEnvioUpdateComponent,
    resolve: {
      planoRecursoTipoEnvio: PlanoRecursoTipoEnvioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.planoRecursoTipoEnvio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlanoRecursoTipoEnvioUpdateComponent,
    resolve: {
      planoRecursoTipoEnvio: PlanoRecursoTipoEnvioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.planoRecursoTipoEnvio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

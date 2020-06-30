import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoEnvioIntegrador, TipoEnvioIntegrador } from 'app/shared/model/tipo-envio-integrador.model';
import { TipoEnvioIntegradorService } from './tipo-envio-integrador.service';
import { TipoEnvioIntegradorComponent } from './tipo-envio-integrador.component';
import { TipoEnvioIntegradorDetailComponent } from './tipo-envio-integrador-detail.component';
import { TipoEnvioIntegradorUpdateComponent } from './tipo-envio-integrador-update.component';

@Injectable({ providedIn: 'root' })
export class TipoEnvioIntegradorResolve implements Resolve<ITipoEnvioIntegrador> {
  constructor(private service: TipoEnvioIntegradorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoEnvioIntegrador> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoEnvioIntegrador: HttpResponse<TipoEnvioIntegrador>) => {
          if (tipoEnvioIntegrador.body) {
            return of(tipoEnvioIntegrador.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoEnvioIntegrador());
  }
}

export const tipoEnvioIntegradorRoute: Routes = [
  {
    path: '',
    component: TipoEnvioIntegradorComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.tipoEnvioIntegrador.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TipoEnvioIntegradorDetailComponent,
    resolve: {
      tipoEnvioIntegrador: TipoEnvioIntegradorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.tipoEnvioIntegrador.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TipoEnvioIntegradorUpdateComponent,
    resolve: {
      tipoEnvioIntegrador: TipoEnvioIntegradorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.tipoEnvioIntegrador.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TipoEnvioIntegradorUpdateComponent,
    resolve: {
      tipoEnvioIntegrador: TipoEnvioIntegradorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.tipoEnvioIntegrador.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

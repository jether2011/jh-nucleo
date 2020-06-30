import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoEnvio, TipoEnvio } from 'app/shared/model/tipo-envio.model';
import { TipoEnvioService } from './tipo-envio.service';
import { TipoEnvioComponent } from './tipo-envio.component';
import { TipoEnvioDetailComponent } from './tipo-envio-detail.component';
import { TipoEnvioUpdateComponent } from './tipo-envio-update.component';

@Injectable({ providedIn: 'root' })
export class TipoEnvioResolve implements Resolve<ITipoEnvio> {
  constructor(private service: TipoEnvioService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoEnvio> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoEnvio: HttpResponse<TipoEnvio>) => {
          if (tipoEnvio.body) {
            return of(tipoEnvio.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoEnvio());
  }
}

export const tipoEnvioRoute: Routes = [
  {
    path: '',
    component: TipoEnvioComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.tipoEnvio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TipoEnvioDetailComponent,
    resolve: {
      tipoEnvio: TipoEnvioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.tipoEnvio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TipoEnvioUpdateComponent,
    resolve: {
      tipoEnvio: TipoEnvioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.tipoEnvio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TipoEnvioUpdateComponent,
    resolve: {
      tipoEnvio: TipoEnvioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.tipoEnvio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

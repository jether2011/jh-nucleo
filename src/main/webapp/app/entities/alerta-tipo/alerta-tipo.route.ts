import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAlertaTipo, AlertaTipo } from 'app/shared/model/alerta-tipo.model';
import { AlertaTipoService } from './alerta-tipo.service';
import { AlertaTipoComponent } from './alerta-tipo.component';
import { AlertaTipoDetailComponent } from './alerta-tipo-detail.component';
import { AlertaTipoUpdateComponent } from './alerta-tipo-update.component';

@Injectable({ providedIn: 'root' })
export class AlertaTipoResolve implements Resolve<IAlertaTipo> {
  constructor(private service: AlertaTipoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAlertaTipo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((alertaTipo: HttpResponse<AlertaTipo>) => {
          if (alertaTipo.body) {
            return of(alertaTipo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AlertaTipo());
  }
}

export const alertaTipoRoute: Routes = [
  {
    path: '',
    component: AlertaTipoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.alertaTipo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AlertaTipoDetailComponent,
    resolve: {
      alertaTipo: AlertaTipoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.alertaTipo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AlertaTipoUpdateComponent,
    resolve: {
      alertaTipo: AlertaTipoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.alertaTipo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AlertaTipoUpdateComponent,
    resolve: {
      alertaTipo: AlertaTipoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.alertaTipo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAlertaRisco, AlertaRisco } from 'app/shared/model/alerta-risco.model';
import { AlertaRiscoService } from './alerta-risco.service';
import { AlertaRiscoComponent } from './alerta-risco.component';
import { AlertaRiscoDetailComponent } from './alerta-risco-detail.component';
import { AlertaRiscoUpdateComponent } from './alerta-risco-update.component';

@Injectable({ providedIn: 'root' })
export class AlertaRiscoResolve implements Resolve<IAlertaRisco> {
  constructor(private service: AlertaRiscoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAlertaRisco> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((alertaRisco: HttpResponse<AlertaRisco>) => {
          if (alertaRisco.body) {
            return of(alertaRisco.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AlertaRisco());
  }
}

export const alertaRiscoRoute: Routes = [
  {
    path: '',
    component: AlertaRiscoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.alertaRisco.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AlertaRiscoDetailComponent,
    resolve: {
      alertaRisco: AlertaRiscoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.alertaRisco.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AlertaRiscoUpdateComponent,
    resolve: {
      alertaRisco: AlertaRiscoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.alertaRisco.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AlertaRiscoUpdateComponent,
    resolve: {
      alertaRisco: AlertaRiscoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.alertaRisco.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

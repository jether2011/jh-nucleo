import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAlertaFerramenta, AlertaFerramenta } from 'app/shared/model/alerta-ferramenta.model';
import { AlertaFerramentaService } from './alerta-ferramenta.service';
import { AlertaFerramentaComponent } from './alerta-ferramenta.component';
import { AlertaFerramentaDetailComponent } from './alerta-ferramenta-detail.component';
import { AlertaFerramentaUpdateComponent } from './alerta-ferramenta-update.component';

@Injectable({ providedIn: 'root' })
export class AlertaFerramentaResolve implements Resolve<IAlertaFerramenta> {
  constructor(private service: AlertaFerramentaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAlertaFerramenta> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((alertaFerramenta: HttpResponse<AlertaFerramenta>) => {
          if (alertaFerramenta.body) {
            return of(alertaFerramenta.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AlertaFerramenta());
  }
}

export const alertaFerramentaRoute: Routes = [
  {
    path: '',
    component: AlertaFerramentaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.alertaFerramenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AlertaFerramentaDetailComponent,
    resolve: {
      alertaFerramenta: AlertaFerramentaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.alertaFerramenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AlertaFerramentaUpdateComponent,
    resolve: {
      alertaFerramenta: AlertaFerramentaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.alertaFerramenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AlertaFerramentaUpdateComponent,
    resolve: {
      alertaFerramenta: AlertaFerramentaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.alertaFerramenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

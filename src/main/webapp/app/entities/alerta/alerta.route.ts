import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAlerta, Alerta } from 'app/shared/model/alerta.model';
import { AlertaService } from './alerta.service';
import { AlertaComponent } from './alerta.component';
import { AlertaDetailComponent } from './alerta-detail.component';
import { AlertaUpdateComponent } from './alerta-update.component';

@Injectable({ providedIn: 'root' })
export class AlertaResolve implements Resolve<IAlerta> {
  constructor(private service: AlertaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAlerta> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((alerta: HttpResponse<Alerta>) => {
          if (alerta.body) {
            return of(alerta.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Alerta());
  }
}

export const alertaRoute: Routes = [
  {
    path: '',
    component: AlertaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.alerta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AlertaDetailComponent,
    resolve: {
      alerta: AlertaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.alerta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AlertaUpdateComponent,
    resolve: {
      alerta: AlertaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.alerta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AlertaUpdateComponent,
    resolve: {
      alerta: AlertaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.alerta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

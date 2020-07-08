import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIntegrador, Integrador } from 'app/shared/model/integrador.model';
import { IntegradorService } from './integrador.service';
import { IntegradorComponent } from './integrador.component';
import { IntegradorDetailComponent } from './integrador-detail.component';
import { IntegradorUpdateComponent } from './integrador-update.component';

@Injectable({ providedIn: 'root' })
export class IntegradorResolve implements Resolve<IIntegrador> {
  constructor(private service: IntegradorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIntegrador> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((integrador: HttpResponse<Integrador>) => {
          if (integrador.body) {
            return of(integrador.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Integrador());
  }
}

export const integradorRoute: Routes = [
  {
    path: '',
    component: IntegradorComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.integrador.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IntegradorDetailComponent,
    resolve: {
      integrador: IntegradorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.integrador.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IntegradorUpdateComponent,
    resolve: {
      integrador: IntegradorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.integrador.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IntegradorUpdateComponent,
    resolve: {
      integrador: IntegradorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.integrador.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

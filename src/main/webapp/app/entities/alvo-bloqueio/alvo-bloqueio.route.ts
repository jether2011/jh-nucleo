import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAlvoBloqueio, AlvoBloqueio } from 'app/shared/model/alvo-bloqueio.model';
import { AlvoBloqueioService } from './alvo-bloqueio.service';
import { AlvoBloqueioComponent } from './alvo-bloqueio.component';
import { AlvoBloqueioDetailComponent } from './alvo-bloqueio-detail.component';
import { AlvoBloqueioUpdateComponent } from './alvo-bloqueio-update.component';

@Injectable({ providedIn: 'root' })
export class AlvoBloqueioResolve implements Resolve<IAlvoBloqueio> {
  constructor(private service: AlvoBloqueioService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAlvoBloqueio> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((alvoBloqueio: HttpResponse<AlvoBloqueio>) => {
          if (alvoBloqueio.body) {
            return of(alvoBloqueio.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AlvoBloqueio());
  }
}

export const alvoBloqueioRoute: Routes = [
  {
    path: '',
    component: AlvoBloqueioComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.alvoBloqueio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AlvoBloqueioDetailComponent,
    resolve: {
      alvoBloqueio: AlvoBloqueioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.alvoBloqueio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AlvoBloqueioUpdateComponent,
    resolve: {
      alvoBloqueio: AlvoBloqueioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.alvoBloqueio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AlvoBloqueioUpdateComponent,
    resolve: {
      alvoBloqueio: AlvoBloqueioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.alvoBloqueio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

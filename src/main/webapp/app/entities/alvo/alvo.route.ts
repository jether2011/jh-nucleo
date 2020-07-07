import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAlvo, Alvo } from 'app/shared/model/alvo.model';
import { AlvoService } from './alvo.service';
import { AlvoComponent } from './alvo.component';
import { AlvoDetailComponent } from './alvo-detail.component';
import { AlvoUpdateComponent } from './alvo-update.component';

@Injectable({ providedIn: 'root' })
export class AlvoResolve implements Resolve<IAlvo> {
  constructor(private service: AlvoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAlvo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((alvo: HttpResponse<Alvo>) => {
          if (alvo.body) {
            return of(alvo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Alvo());
  }
}

export const alvoRoute: Routes = [
  {
    path: '',
    component: AlvoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.alvo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AlvoDetailComponent,
    resolve: {
      alvo: AlvoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.alvo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AlvoUpdateComponent,
    resolve: {
      alvo: AlvoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.alvo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AlvoUpdateComponent,
    resolve: {
      alvo: AlvoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.alvo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

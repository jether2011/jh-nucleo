import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlano, Plano } from 'app/shared/model/plano.model';
import { PlanoService } from './plano.service';
import { PlanoComponent } from './plano.component';
import { PlanoDetailComponent } from './plano-detail.component';
import { PlanoUpdateComponent } from './plano-update.component';

@Injectable({ providedIn: 'root' })
export class PlanoResolve implements Resolve<IPlano> {
  constructor(private service: PlanoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlano> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((plano: HttpResponse<Plano>) => {
          if (plano.body) {
            return of(plano.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Plano());
  }
}

export const planoRoute: Routes = [
  {
    path: '',
    component: PlanoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.plano.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlanoDetailComponent,
    resolve: {
      plano: PlanoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.plano.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlanoUpdateComponent,
    resolve: {
      plano: PlanoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.plano.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlanoUpdateComponent,
    resolve: {
      plano: PlanoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.plano.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

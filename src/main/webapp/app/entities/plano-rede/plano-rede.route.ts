import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlanoRede, PlanoRede } from 'app/shared/model/plano-rede.model';
import { PlanoRedeService } from './plano-rede.service';
import { PlanoRedeComponent } from './plano-rede.component';
import { PlanoRedeDetailComponent } from './plano-rede-detail.component';
import { PlanoRedeUpdateComponent } from './plano-rede-update.component';

@Injectable({ providedIn: 'root' })
export class PlanoRedeResolve implements Resolve<IPlanoRede> {
  constructor(private service: PlanoRedeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlanoRede> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((planoRede: HttpResponse<PlanoRede>) => {
          if (planoRede.body) {
            return of(planoRede.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlanoRede());
  }
}

export const planoRedeRoute: Routes = [
  {
    path: '',
    component: PlanoRedeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.planoRede.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlanoRedeDetailComponent,
    resolve: {
      planoRede: PlanoRedeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.planoRede.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlanoRedeUpdateComponent,
    resolve: {
      planoRede: PlanoRedeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.planoRede.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlanoRedeUpdateComponent,
    resolve: {
      planoRede: PlanoRedeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.planoRede.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

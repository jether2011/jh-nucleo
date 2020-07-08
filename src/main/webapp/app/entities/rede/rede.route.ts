import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRede, Rede } from 'app/shared/model/rede.model';
import { RedeService } from './rede.service';
import { RedeComponent } from './rede.component';
import { RedeDetailComponent } from './rede-detail.component';
import { RedeUpdateComponent } from './rede-update.component';

@Injectable({ providedIn: 'root' })
export class RedeResolve implements Resolve<IRede> {
  constructor(private service: RedeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRede> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((rede: HttpResponse<Rede>) => {
          if (rede.body) {
            return of(rede.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Rede());
  }
}

export const redeRoute: Routes = [
  {
    path: '',
    component: RedeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.rede.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RedeDetailComponent,
    resolve: {
      rede: RedeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.rede.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RedeUpdateComponent,
    resolve: {
      rede: RedeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.rede.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RedeUpdateComponent,
    resolve: {
      rede: RedeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.rede.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

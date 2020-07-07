import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPrevisao, Previsao } from 'app/shared/model/previsao.model';
import { PrevisaoService } from './previsao.service';
import { PrevisaoComponent } from './previsao.component';
import { PrevisaoDetailComponent } from './previsao-detail.component';
import { PrevisaoUpdateComponent } from './previsao-update.component';

@Injectable({ providedIn: 'root' })
export class PrevisaoResolve implements Resolve<IPrevisao> {
  constructor(private service: PrevisaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrevisao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((previsao: HttpResponse<Previsao>) => {
          if (previsao.body) {
            return of(previsao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Previsao());
  }
}

export const previsaoRoute: Routes = [
  {
    path: '',
    component: PrevisaoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.previsao.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PrevisaoDetailComponent,
    resolve: {
      previsao: PrevisaoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.previsao.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PrevisaoUpdateComponent,
    resolve: {
      previsao: PrevisaoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.previsao.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PrevisaoUpdateComponent,
    resolve: {
      previsao: PrevisaoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.previsao.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

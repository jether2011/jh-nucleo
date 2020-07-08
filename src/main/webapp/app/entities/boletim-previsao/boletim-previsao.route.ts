import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBoletimPrevisao, BoletimPrevisao } from 'app/shared/model/boletim-previsao.model';
import { BoletimPrevisaoService } from './boletim-previsao.service';
import { BoletimPrevisaoComponent } from './boletim-previsao.component';
import { BoletimPrevisaoDetailComponent } from './boletim-previsao-detail.component';
import { BoletimPrevisaoUpdateComponent } from './boletim-previsao-update.component';

@Injectable({ providedIn: 'root' })
export class BoletimPrevisaoResolve implements Resolve<IBoletimPrevisao> {
  constructor(private service: BoletimPrevisaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBoletimPrevisao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((boletimPrevisao: HttpResponse<BoletimPrevisao>) => {
          if (boletimPrevisao.body) {
            return of(boletimPrevisao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BoletimPrevisao());
  }
}

export const boletimPrevisaoRoute: Routes = [
  {
    path: '',
    component: BoletimPrevisaoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.boletimPrevisao.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BoletimPrevisaoDetailComponent,
    resolve: {
      boletimPrevisao: BoletimPrevisaoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.boletimPrevisao.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BoletimPrevisaoUpdateComponent,
    resolve: {
      boletimPrevisao: BoletimPrevisaoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.boletimPrevisao.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BoletimPrevisaoUpdateComponent,
    resolve: {
      boletimPrevisao: BoletimPrevisaoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.boletimPrevisao.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

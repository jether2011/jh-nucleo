import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBoletimPrevVariavelMet, BoletimPrevVariavelMet } from 'app/shared/model/boletim-prev-variavel-met.model';
import { BoletimPrevVariavelMetService } from './boletim-prev-variavel-met.service';
import { BoletimPrevVariavelMetComponent } from './boletim-prev-variavel-met.component';
import { BoletimPrevVariavelMetDetailComponent } from './boletim-prev-variavel-met-detail.component';
import { BoletimPrevVariavelMetUpdateComponent } from './boletim-prev-variavel-met-update.component';

@Injectable({ providedIn: 'root' })
export class BoletimPrevVariavelMetResolve implements Resolve<IBoletimPrevVariavelMet> {
  constructor(private service: BoletimPrevVariavelMetService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBoletimPrevVariavelMet> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((boletimPrevVariavelMet: HttpResponse<BoletimPrevVariavelMet>) => {
          if (boletimPrevVariavelMet.body) {
            return of(boletimPrevVariavelMet.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BoletimPrevVariavelMet());
  }
}

export const boletimPrevVariavelMetRoute: Routes = [
  {
    path: '',
    component: BoletimPrevVariavelMetComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.boletimPrevVariavelMet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BoletimPrevVariavelMetDetailComponent,
    resolve: {
      boletimPrevVariavelMet: BoletimPrevVariavelMetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.boletimPrevVariavelMet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BoletimPrevVariavelMetUpdateComponent,
    resolve: {
      boletimPrevVariavelMet: BoletimPrevVariavelMetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.boletimPrevVariavelMet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BoletimPrevVariavelMetUpdateComponent,
    resolve: {
      boletimPrevVariavelMet: BoletimPrevVariavelMetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.boletimPrevVariavelMet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

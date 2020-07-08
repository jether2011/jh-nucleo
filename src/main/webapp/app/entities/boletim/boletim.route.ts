import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBoletim, Boletim } from 'app/shared/model/boletim.model';
import { BoletimService } from './boletim.service';
import { BoletimComponent } from './boletim.component';
import { BoletimDetailComponent } from './boletim-detail.component';
import { BoletimUpdateComponent } from './boletim-update.component';

@Injectable({ providedIn: 'root' })
export class BoletimResolve implements Resolve<IBoletim> {
  constructor(private service: BoletimService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBoletim> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((boletim: HttpResponse<Boletim>) => {
          if (boletim.body) {
            return of(boletim.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Boletim());
  }
}

export const boletimRoute: Routes = [
  {
    path: '',
    component: BoletimComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.boletim.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BoletimDetailComponent,
    resolve: {
      boletim: BoletimResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.boletim.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BoletimUpdateComponent,
    resolve: {
      boletim: BoletimResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.boletim.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BoletimUpdateComponent,
    resolve: {
      boletim: BoletimResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.boletim.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBoletimPrevObs, BoletimPrevObs } from 'app/shared/model/boletim-prev-obs.model';
import { BoletimPrevObsService } from './boletim-prev-obs.service';
import { BoletimPrevObsComponent } from './boletim-prev-obs.component';
import { BoletimPrevObsDetailComponent } from './boletim-prev-obs-detail.component';
import { BoletimPrevObsUpdateComponent } from './boletim-prev-obs-update.component';

@Injectable({ providedIn: 'root' })
export class BoletimPrevObsResolve implements Resolve<IBoletimPrevObs> {
  constructor(private service: BoletimPrevObsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBoletimPrevObs> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((boletimPrevObs: HttpResponse<BoletimPrevObs>) => {
          if (boletimPrevObs.body) {
            return of(boletimPrevObs.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BoletimPrevObs());
  }
}

export const boletimPrevObsRoute: Routes = [
  {
    path: '',
    component: BoletimPrevObsComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.boletimPrevObs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BoletimPrevObsDetailComponent,
    resolve: {
      boletimPrevObs: BoletimPrevObsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.boletimPrevObs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BoletimPrevObsUpdateComponent,
    resolve: {
      boletimPrevObs: BoletimPrevObsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.boletimPrevObs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BoletimPrevObsUpdateComponent,
    resolve: {
      boletimPrevObs: BoletimPrevObsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.boletimPrevObs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

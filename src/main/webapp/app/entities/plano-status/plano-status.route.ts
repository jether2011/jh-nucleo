import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlanoStatus, PlanoStatus } from 'app/shared/model/plano-status.model';
import { PlanoStatusService } from './plano-status.service';
import { PlanoStatusComponent } from './plano-status.component';
import { PlanoStatusDetailComponent } from './plano-status-detail.component';
import { PlanoStatusUpdateComponent } from './plano-status-update.component';

@Injectable({ providedIn: 'root' })
export class PlanoStatusResolve implements Resolve<IPlanoStatus> {
  constructor(private service: PlanoStatusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlanoStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((planoStatus: HttpResponse<PlanoStatus>) => {
          if (planoStatus.body) {
            return of(planoStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlanoStatus());
  }
}

export const planoStatusRoute: Routes = [
  {
    path: '',
    component: PlanoStatusComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.planoStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlanoStatusDetailComponent,
    resolve: {
      planoStatus: PlanoStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.planoStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlanoStatusUpdateComponent,
    resolve: {
      planoStatus: PlanoStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.planoStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlanoStatusUpdateComponent,
    resolve: {
      planoStatus: PlanoStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.planoStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

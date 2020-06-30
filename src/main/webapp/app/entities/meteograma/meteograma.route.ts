import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMeteograma, Meteograma } from 'app/shared/model/meteograma.model';
import { MeteogramaService } from './meteograma.service';
import { MeteogramaComponent } from './meteograma.component';
import { MeteogramaDetailComponent } from './meteograma-detail.component';
import { MeteogramaUpdateComponent } from './meteograma-update.component';

@Injectable({ providedIn: 'root' })
export class MeteogramaResolve implements Resolve<IMeteograma> {
  constructor(private service: MeteogramaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMeteograma> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((meteograma: HttpResponse<Meteograma>) => {
          if (meteograma.body) {
            return of(meteograma.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Meteograma());
  }
}

export const meteogramaRoute: Routes = [
  {
    path: '',
    component: MeteogramaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.meteograma.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MeteogramaDetailComponent,
    resolve: {
      meteograma: MeteogramaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.meteograma.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MeteogramaUpdateComponent,
    resolve: {
      meteograma: MeteogramaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.meteograma.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MeteogramaUpdateComponent,
    resolve: {
      meteograma: MeteogramaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.meteograma.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

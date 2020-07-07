import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUmidadeAr, UmidadeAr } from 'app/shared/model/umidade-ar.model';
import { UmidadeArService } from './umidade-ar.service';
import { UmidadeArComponent } from './umidade-ar.component';
import { UmidadeArDetailComponent } from './umidade-ar-detail.component';
import { UmidadeArUpdateComponent } from './umidade-ar-update.component';

@Injectable({ providedIn: 'root' })
export class UmidadeArResolve implements Resolve<IUmidadeAr> {
  constructor(private service: UmidadeArService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUmidadeAr> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((umidadeAr: HttpResponse<UmidadeAr>) => {
          if (umidadeAr.body) {
            return of(umidadeAr.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UmidadeAr());
  }
}

export const umidadeArRoute: Routes = [
  {
    path: '',
    component: UmidadeArComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.umidadeAr.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UmidadeArDetailComponent,
    resolve: {
      umidadeAr: UmidadeArResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.umidadeAr.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UmidadeArUpdateComponent,
    resolve: {
      umidadeAr: UmidadeArResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.umidadeAr.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UmidadeArUpdateComponent,
    resolve: {
      umidadeAr: UmidadeArResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.umidadeAr.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

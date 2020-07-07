import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITempestadeNivel, TempestadeNivel } from 'app/shared/model/tempestade-nivel.model';
import { TempestadeNivelService } from './tempestade-nivel.service';
import { TempestadeNivelComponent } from './tempestade-nivel.component';
import { TempestadeNivelDetailComponent } from './tempestade-nivel-detail.component';
import { TempestadeNivelUpdateComponent } from './tempestade-nivel-update.component';

@Injectable({ providedIn: 'root' })
export class TempestadeNivelResolve implements Resolve<ITempestadeNivel> {
  constructor(private service: TempestadeNivelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITempestadeNivel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tempestadeNivel: HttpResponse<TempestadeNivel>) => {
          if (tempestadeNivel.body) {
            return of(tempestadeNivel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TempestadeNivel());
  }
}

export const tempestadeNivelRoute: Routes = [
  {
    path: '',
    component: TempestadeNivelComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.tempestadeNivel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TempestadeNivelDetailComponent,
    resolve: {
      tempestadeNivel: TempestadeNivelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.tempestadeNivel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TempestadeNivelUpdateComponent,
    resolve: {
      tempestadeNivel: TempestadeNivelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.tempestadeNivel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TempestadeNivelUpdateComponent,
    resolve: {
      tempestadeNivel: TempestadeNivelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.tempestadeNivel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

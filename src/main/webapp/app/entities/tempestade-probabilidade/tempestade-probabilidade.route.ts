import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITempestadeProbabilidade, TempestadeProbabilidade } from 'app/shared/model/tempestade-probabilidade.model';
import { TempestadeProbabilidadeService } from './tempestade-probabilidade.service';
import { TempestadeProbabilidadeComponent } from './tempestade-probabilidade.component';
import { TempestadeProbabilidadeDetailComponent } from './tempestade-probabilidade-detail.component';
import { TempestadeProbabilidadeUpdateComponent } from './tempestade-probabilidade-update.component';

@Injectable({ providedIn: 'root' })
export class TempestadeProbabilidadeResolve implements Resolve<ITempestadeProbabilidade> {
  constructor(private service: TempestadeProbabilidadeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITempestadeProbabilidade> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tempestadeProbabilidade: HttpResponse<TempestadeProbabilidade>) => {
          if (tempestadeProbabilidade.body) {
            return of(tempestadeProbabilidade.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TempestadeProbabilidade());
  }
}

export const tempestadeProbabilidadeRoute: Routes = [
  {
    path: '',
    component: TempestadeProbabilidadeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.tempestadeProbabilidade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TempestadeProbabilidadeDetailComponent,
    resolve: {
      tempestadeProbabilidade: TempestadeProbabilidadeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.tempestadeProbabilidade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TempestadeProbabilidadeUpdateComponent,
    resolve: {
      tempestadeProbabilidade: TempestadeProbabilidadeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.tempestadeProbabilidade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TempestadeProbabilidadeUpdateComponent,
    resolve: {
      tempestadeProbabilidade: TempestadeProbabilidadeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.tempestadeProbabilidade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

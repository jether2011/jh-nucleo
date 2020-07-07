import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDiaSemana, DiaSemana } from 'app/shared/model/dia-semana.model';
import { DiaSemanaService } from './dia-semana.service';
import { DiaSemanaComponent } from './dia-semana.component';
import { DiaSemanaDetailComponent } from './dia-semana-detail.component';
import { DiaSemanaUpdateComponent } from './dia-semana-update.component';

@Injectable({ providedIn: 'root' })
export class DiaSemanaResolve implements Resolve<IDiaSemana> {
  constructor(private service: DiaSemanaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDiaSemana> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((diaSemana: HttpResponse<DiaSemana>) => {
          if (diaSemana.body) {
            return of(diaSemana.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DiaSemana());
  }
}

export const diaSemanaRoute: Routes = [
  {
    path: '',
    component: DiaSemanaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.diaSemana.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DiaSemanaDetailComponent,
    resolve: {
      diaSemana: DiaSemanaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.diaSemana.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DiaSemanaUpdateComponent,
    resolve: {
      diaSemana: DiaSemanaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.diaSemana.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DiaSemanaUpdateComponent,
    resolve: {
      diaSemana: DiaSemanaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.diaSemana.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

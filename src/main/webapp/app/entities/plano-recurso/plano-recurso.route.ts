import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlanoRecurso, PlanoRecurso } from 'app/shared/model/plano-recurso.model';
import { PlanoRecursoService } from './plano-recurso.service';
import { PlanoRecursoComponent } from './plano-recurso.component';
import { PlanoRecursoDetailComponent } from './plano-recurso-detail.component';
import { PlanoRecursoUpdateComponent } from './plano-recurso-update.component';

@Injectable({ providedIn: 'root' })
export class PlanoRecursoResolve implements Resolve<IPlanoRecurso> {
  constructor(private service: PlanoRecursoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlanoRecurso> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((planoRecurso: HttpResponse<PlanoRecurso>) => {
          if (planoRecurso.body) {
            return of(planoRecurso.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlanoRecurso());
  }
}

export const planoRecursoRoute: Routes = [
  {
    path: '',
    component: PlanoRecursoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.planoRecurso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlanoRecursoDetailComponent,
    resolve: {
      planoRecurso: PlanoRecursoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.planoRecurso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlanoRecursoUpdateComponent,
    resolve: {
      planoRecurso: PlanoRecursoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.planoRecurso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlanoRecursoUpdateComponent,
    resolve: {
      planoRecurso: PlanoRecursoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.planoRecurso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

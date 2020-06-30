import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRecursoTipo, RecursoTipo } from 'app/shared/model/recurso-tipo.model';
import { RecursoTipoService } from './recurso-tipo.service';
import { RecursoTipoComponent } from './recurso-tipo.component';
import { RecursoTipoDetailComponent } from './recurso-tipo-detail.component';
import { RecursoTipoUpdateComponent } from './recurso-tipo-update.component';

@Injectable({ providedIn: 'root' })
export class RecursoTipoResolve implements Resolve<IRecursoTipo> {
  constructor(private service: RecursoTipoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRecursoTipo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((recursoTipo: HttpResponse<RecursoTipo>) => {
          if (recursoTipo.body) {
            return of(recursoTipo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RecursoTipo());
  }
}

export const recursoTipoRoute: Routes = [
  {
    path: '',
    component: RecursoTipoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.recursoTipo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RecursoTipoDetailComponent,
    resolve: {
      recursoTipo: RecursoTipoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.recursoTipo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RecursoTipoUpdateComponent,
    resolve: {
      recursoTipo: RecursoTipoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.recursoTipo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RecursoTipoUpdateComponent,
    resolve: {
      recursoTipo: RecursoTipoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.recursoTipo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

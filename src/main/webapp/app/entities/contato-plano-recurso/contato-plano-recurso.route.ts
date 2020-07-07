import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContatoPlanoRecurso, ContatoPlanoRecurso } from 'app/shared/model/contato-plano-recurso.model';
import { ContatoPlanoRecursoService } from './contato-plano-recurso.service';
import { ContatoPlanoRecursoComponent } from './contato-plano-recurso.component';
import { ContatoPlanoRecursoDetailComponent } from './contato-plano-recurso-detail.component';
import { ContatoPlanoRecursoUpdateComponent } from './contato-plano-recurso-update.component';

@Injectable({ providedIn: 'root' })
export class ContatoPlanoRecursoResolve implements Resolve<IContatoPlanoRecurso> {
  constructor(private service: ContatoPlanoRecursoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContatoPlanoRecurso> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contatoPlanoRecurso: HttpResponse<ContatoPlanoRecurso>) => {
          if (contatoPlanoRecurso.body) {
            return of(contatoPlanoRecurso.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContatoPlanoRecurso());
  }
}

export const contatoPlanoRecursoRoute: Routes = [
  {
    path: '',
    component: ContatoPlanoRecursoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.contatoPlanoRecurso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContatoPlanoRecursoDetailComponent,
    resolve: {
      contatoPlanoRecurso: ContatoPlanoRecursoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.contatoPlanoRecurso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContatoPlanoRecursoUpdateComponent,
    resolve: {
      contatoPlanoRecurso: ContatoPlanoRecursoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.contatoPlanoRecurso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContatoPlanoRecursoUpdateComponent,
    resolve: {
      contatoPlanoRecurso: ContatoPlanoRecursoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.contatoPlanoRecurso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

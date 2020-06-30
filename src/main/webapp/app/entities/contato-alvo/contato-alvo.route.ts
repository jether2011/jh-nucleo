import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContatoAlvo, ContatoAlvo } from 'app/shared/model/contato-alvo.model';
import { ContatoAlvoService } from './contato-alvo.service';
import { ContatoAlvoComponent } from './contato-alvo.component';
import { ContatoAlvoDetailComponent } from './contato-alvo-detail.component';
import { ContatoAlvoUpdateComponent } from './contato-alvo-update.component';

@Injectable({ providedIn: 'root' })
export class ContatoAlvoResolve implements Resolve<IContatoAlvo> {
  constructor(private service: ContatoAlvoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContatoAlvo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contatoAlvo: HttpResponse<ContatoAlvo>) => {
          if (contatoAlvo.body) {
            return of(contatoAlvo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContatoAlvo());
  }
}

export const contatoAlvoRoute: Routes = [
  {
    path: '',
    component: ContatoAlvoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.contatoAlvo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContatoAlvoDetailComponent,
    resolve: {
      contatoAlvo: ContatoAlvoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.contatoAlvo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContatoAlvoUpdateComponent,
    resolve: {
      contatoAlvo: ContatoAlvoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.contatoAlvo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContatoAlvoUpdateComponent,
    resolve: {
      contatoAlvo: ContatoAlvoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.contatoAlvo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

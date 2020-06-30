import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConsolidacao, Consolidacao } from 'app/shared/model/consolidacao.model';
import { ConsolidacaoService } from './consolidacao.service';
import { ConsolidacaoComponent } from './consolidacao.component';
import { ConsolidacaoDetailComponent } from './consolidacao-detail.component';
import { ConsolidacaoUpdateComponent } from './consolidacao-update.component';

@Injectable({ providedIn: 'root' })
export class ConsolidacaoResolve implements Resolve<IConsolidacao> {
  constructor(private service: ConsolidacaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConsolidacao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((consolidacao: HttpResponse<Consolidacao>) => {
          if (consolidacao.body) {
            return of(consolidacao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Consolidacao());
  }
}

export const consolidacaoRoute: Routes = [
  {
    path: '',
    component: ConsolidacaoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.consolidacao.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConsolidacaoDetailComponent,
    resolve: {
      consolidacao: ConsolidacaoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.consolidacao.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConsolidacaoUpdateComponent,
    resolve: {
      consolidacao: ConsolidacaoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.consolidacao.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConsolidacaoUpdateComponent,
    resolve: {
      consolidacao: ConsolidacaoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.consolidacao.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

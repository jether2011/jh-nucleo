import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICondicaoTempo, CondicaoTempo } from 'app/shared/model/condicao-tempo.model';
import { CondicaoTempoService } from './condicao-tempo.service';
import { CondicaoTempoComponent } from './condicao-tempo.component';
import { CondicaoTempoDetailComponent } from './condicao-tempo-detail.component';
import { CondicaoTempoUpdateComponent } from './condicao-tempo-update.component';

@Injectable({ providedIn: 'root' })
export class CondicaoTempoResolve implements Resolve<ICondicaoTempo> {
  constructor(private service: CondicaoTempoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICondicaoTempo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((condicaoTempo: HttpResponse<CondicaoTempo>) => {
          if (condicaoTempo.body) {
            return of(condicaoTempo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CondicaoTempo());
  }
}

export const condicaoTempoRoute: Routes = [
  {
    path: '',
    component: CondicaoTempoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.condicaoTempo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CondicaoTempoDetailComponent,
    resolve: {
      condicaoTempo: CondicaoTempoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.condicaoTempo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CondicaoTempoUpdateComponent,
    resolve: {
      condicaoTempo: CondicaoTempoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.condicaoTempo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CondicaoTempoUpdateComponent,
    resolve: {
      condicaoTempo: CondicaoTempoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.condicaoTempo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

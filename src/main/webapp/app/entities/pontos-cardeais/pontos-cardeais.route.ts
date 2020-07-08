import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPontosCardeais, PontosCardeais } from 'app/shared/model/pontos-cardeais.model';
import { PontosCardeaisService } from './pontos-cardeais.service';
import { PontosCardeaisComponent } from './pontos-cardeais.component';
import { PontosCardeaisDetailComponent } from './pontos-cardeais-detail.component';
import { PontosCardeaisUpdateComponent } from './pontos-cardeais-update.component';

@Injectable({ providedIn: 'root' })
export class PontosCardeaisResolve implements Resolve<IPontosCardeais> {
  constructor(private service: PontosCardeaisService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPontosCardeais> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pontosCardeais: HttpResponse<PontosCardeais>) => {
          if (pontosCardeais.body) {
            return of(pontosCardeais.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PontosCardeais());
  }
}

export const pontosCardeaisRoute: Routes = [
  {
    path: '',
    component: PontosCardeaisComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.pontosCardeais.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PontosCardeaisDetailComponent,
    resolve: {
      pontosCardeais: PontosCardeaisResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.pontosCardeais.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PontosCardeaisUpdateComponent,
    resolve: {
      pontosCardeais: PontosCardeaisResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.pontosCardeais.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PontosCardeaisUpdateComponent,
    resolve: {
      pontosCardeais: PontosCardeaisResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.pontosCardeais.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

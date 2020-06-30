import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJornadaTrabalho, JornadaTrabalho } from 'app/shared/model/jornada-trabalho.model';
import { JornadaTrabalhoService } from './jornada-trabalho.service';
import { JornadaTrabalhoComponent } from './jornada-trabalho.component';
import { JornadaTrabalhoDetailComponent } from './jornada-trabalho-detail.component';
import { JornadaTrabalhoUpdateComponent } from './jornada-trabalho-update.component';

@Injectable({ providedIn: 'root' })
export class JornadaTrabalhoResolve implements Resolve<IJornadaTrabalho> {
  constructor(private service: JornadaTrabalhoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJornadaTrabalho> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((jornadaTrabalho: HttpResponse<JornadaTrabalho>) => {
          if (jornadaTrabalho.body) {
            return of(jornadaTrabalho.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new JornadaTrabalho());
  }
}

export const jornadaTrabalhoRoute: Routes = [
  {
    path: '',
    component: JornadaTrabalhoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.jornadaTrabalho.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JornadaTrabalhoDetailComponent,
    resolve: {
      jornadaTrabalho: JornadaTrabalhoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.jornadaTrabalho.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JornadaTrabalhoUpdateComponent,
    resolve: {
      jornadaTrabalho: JornadaTrabalhoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.jornadaTrabalho.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JornadaTrabalhoUpdateComponent,
    resolve: {
      jornadaTrabalho: JornadaTrabalhoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.jornadaTrabalho.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

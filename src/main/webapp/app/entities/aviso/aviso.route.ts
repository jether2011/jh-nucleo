import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAviso, Aviso } from 'app/shared/model/aviso.model';
import { AvisoService } from './aviso.service';
import { AvisoComponent } from './aviso.component';
import { AvisoDetailComponent } from './aviso-detail.component';
import { AvisoUpdateComponent } from './aviso-update.component';

@Injectable({ providedIn: 'root' })
export class AvisoResolve implements Resolve<IAviso> {
  constructor(private service: AvisoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAviso> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((aviso: HttpResponse<Aviso>) => {
          if (aviso.body) {
            return of(aviso.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Aviso());
  }
}

export const avisoRoute: Routes = [
  {
    path: '',
    component: AvisoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.aviso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvisoDetailComponent,
    resolve: {
      aviso: AvisoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.aviso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvisoUpdateComponent,
    resolve: {
      aviso: AvisoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.aviso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvisoUpdateComponent,
    resolve: {
      aviso: AvisoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.aviso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

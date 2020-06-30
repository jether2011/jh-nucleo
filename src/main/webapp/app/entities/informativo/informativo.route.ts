import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInformativo, Informativo } from 'app/shared/model/informativo.model';
import { InformativoService } from './informativo.service';
import { InformativoComponent } from './informativo.component';
import { InformativoDetailComponent } from './informativo-detail.component';
import { InformativoUpdateComponent } from './informativo-update.component';

@Injectable({ providedIn: 'root' })
export class InformativoResolve implements Resolve<IInformativo> {
  constructor(private service: InformativoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInformativo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((informativo: HttpResponse<Informativo>) => {
          if (informativo.body) {
            return of(informativo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Informativo());
  }
}

export const informativoRoute: Routes = [
  {
    path: '',
    component: InformativoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.informativo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InformativoDetailComponent,
    resolve: {
      informativo: InformativoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.informativo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InformativoUpdateComponent,
    resolve: {
      informativo: InformativoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.informativo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InformativoUpdateComponent,
    resolve: {
      informativo: InformativoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.informativo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

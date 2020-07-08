import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDescarga, Descarga } from 'app/shared/model/descarga.model';
import { DescargaService } from './descarga.service';
import { DescargaComponent } from './descarga.component';
import { DescargaDetailComponent } from './descarga-detail.component';
import { DescargaUpdateComponent } from './descarga-update.component';

@Injectable({ providedIn: 'root' })
export class DescargaResolve implements Resolve<IDescarga> {
  constructor(private service: DescargaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDescarga> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((descarga: HttpResponse<Descarga>) => {
          if (descarga.body) {
            return of(descarga.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Descarga());
  }
}

export const descargaRoute: Routes = [
  {
    path: '',
    component: DescargaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.descarga.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DescargaDetailComponent,
    resolve: {
      descarga: DescargaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.descarga.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DescargaUpdateComponent,
    resolve: {
      descarga: DescargaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.descarga.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DescargaUpdateComponent,
    resolve: {
      descarga: DescargaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.descarga.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

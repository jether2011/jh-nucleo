import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDescargaTipo, DescargaTipo } from 'app/shared/model/descarga-tipo.model';
import { DescargaTipoService } from './descarga-tipo.service';
import { DescargaTipoComponent } from './descarga-tipo.component';
import { DescargaTipoDetailComponent } from './descarga-tipo-detail.component';
import { DescargaTipoUpdateComponent } from './descarga-tipo-update.component';

@Injectable({ providedIn: 'root' })
export class DescargaTipoResolve implements Resolve<IDescargaTipo> {
  constructor(private service: DescargaTipoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDescargaTipo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((descargaTipo: HttpResponse<DescargaTipo>) => {
          if (descargaTipo.body) {
            return of(descargaTipo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DescargaTipo());
  }
}

export const descargaTipoRoute: Routes = [
  {
    path: '',
    component: DescargaTipoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.descargaTipo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DescargaTipoDetailComponent,
    resolve: {
      descargaTipo: DescargaTipoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.descargaTipo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DescargaTipoUpdateComponent,
    resolve: {
      descargaTipo: DescargaTipoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.descargaTipo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DescargaTipoUpdateComponent,
    resolve: {
      descargaTipo: DescargaTipoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.descargaTipo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

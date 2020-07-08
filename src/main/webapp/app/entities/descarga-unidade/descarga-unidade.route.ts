import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDescargaUnidade, DescargaUnidade } from 'app/shared/model/descarga-unidade.model';
import { DescargaUnidadeService } from './descarga-unidade.service';
import { DescargaUnidadeComponent } from './descarga-unidade.component';
import { DescargaUnidadeDetailComponent } from './descarga-unidade-detail.component';
import { DescargaUnidadeUpdateComponent } from './descarga-unidade-update.component';

@Injectable({ providedIn: 'root' })
export class DescargaUnidadeResolve implements Resolve<IDescargaUnidade> {
  constructor(private service: DescargaUnidadeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDescargaUnidade> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((descargaUnidade: HttpResponse<DescargaUnidade>) => {
          if (descargaUnidade.body) {
            return of(descargaUnidade.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DescargaUnidade());
  }
}

export const descargaUnidadeRoute: Routes = [
  {
    path: '',
    component: DescargaUnidadeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.descargaUnidade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DescargaUnidadeDetailComponent,
    resolve: {
      descargaUnidade: DescargaUnidadeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.descargaUnidade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DescargaUnidadeUpdateComponent,
    resolve: {
      descargaUnidade: DescargaUnidadeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.descargaUnidade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DescargaUnidadeUpdateComponent,
    resolve: {
      descargaUnidade: DescargaUnidadeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.descargaUnidade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

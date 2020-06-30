import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAcumuladoChuvaFaixa, AcumuladoChuvaFaixa } from 'app/shared/model/acumulado-chuva-faixa.model';
import { AcumuladoChuvaFaixaService } from './acumulado-chuva-faixa.service';
import { AcumuladoChuvaFaixaComponent } from './acumulado-chuva-faixa.component';
import { AcumuladoChuvaFaixaDetailComponent } from './acumulado-chuva-faixa-detail.component';
import { AcumuladoChuvaFaixaUpdateComponent } from './acumulado-chuva-faixa-update.component';

@Injectable({ providedIn: 'root' })
export class AcumuladoChuvaFaixaResolve implements Resolve<IAcumuladoChuvaFaixa> {
  constructor(private service: AcumuladoChuvaFaixaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAcumuladoChuvaFaixa> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((acumuladoChuvaFaixa: HttpResponse<AcumuladoChuvaFaixa>) => {
          if (acumuladoChuvaFaixa.body) {
            return of(acumuladoChuvaFaixa.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AcumuladoChuvaFaixa());
  }
}

export const acumuladoChuvaFaixaRoute: Routes = [
  {
    path: '',
    component: AcumuladoChuvaFaixaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.acumuladoChuvaFaixa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AcumuladoChuvaFaixaDetailComponent,
    resolve: {
      acumuladoChuvaFaixa: AcumuladoChuvaFaixaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.acumuladoChuvaFaixa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AcumuladoChuvaFaixaUpdateComponent,
    resolve: {
      acumuladoChuvaFaixa: AcumuladoChuvaFaixaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.acumuladoChuvaFaixa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AcumuladoChuvaFaixaUpdateComponent,
    resolve: {
      acumuladoChuvaFaixa: AcumuladoChuvaFaixaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.acumuladoChuvaFaixa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

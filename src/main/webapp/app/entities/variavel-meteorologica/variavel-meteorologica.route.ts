import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVariavelMeteorologica, VariavelMeteorologica } from 'app/shared/model/variavel-meteorologica.model';
import { VariavelMeteorologicaService } from './variavel-meteorologica.service';
import { VariavelMeteorologicaComponent } from './variavel-meteorologica.component';
import { VariavelMeteorologicaDetailComponent } from './variavel-meteorologica-detail.component';
import { VariavelMeteorologicaUpdateComponent } from './variavel-meteorologica-update.component';

@Injectable({ providedIn: 'root' })
export class VariavelMeteorologicaResolve implements Resolve<IVariavelMeteorologica> {
  constructor(private service: VariavelMeteorologicaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVariavelMeteorologica> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((variavelMeteorologica: HttpResponse<VariavelMeteorologica>) => {
          if (variavelMeteorologica.body) {
            return of(variavelMeteorologica.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VariavelMeteorologica());
  }
}

export const variavelMeteorologicaRoute: Routes = [
  {
    path: '',
    component: VariavelMeteorologicaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.variavelMeteorologica.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VariavelMeteorologicaDetailComponent,
    resolve: {
      variavelMeteorologica: VariavelMeteorologicaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.variavelMeteorologica.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VariavelMeteorologicaUpdateComponent,
    resolve: {
      variavelMeteorologica: VariavelMeteorologicaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.variavelMeteorologica.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VariavelMeteorologicaUpdateComponent,
    resolve: {
      variavelMeteorologica: VariavelMeteorologicaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.variavelMeteorologica.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

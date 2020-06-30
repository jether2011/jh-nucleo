import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAvisoMeteorologico, AvisoMeteorologico } from 'app/shared/model/aviso-meteorologico.model';
import { AvisoMeteorologicoService } from './aviso-meteorologico.service';
import { AvisoMeteorologicoComponent } from './aviso-meteorologico.component';
import { AvisoMeteorologicoDetailComponent } from './aviso-meteorologico-detail.component';
import { AvisoMeteorologicoUpdateComponent } from './aviso-meteorologico-update.component';

@Injectable({ providedIn: 'root' })
export class AvisoMeteorologicoResolve implements Resolve<IAvisoMeteorologico> {
  constructor(private service: AvisoMeteorologicoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAvisoMeteorologico> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((avisoMeteorologico: HttpResponse<AvisoMeteorologico>) => {
          if (avisoMeteorologico.body) {
            return of(avisoMeteorologico.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AvisoMeteorologico());
  }
}

export const avisoMeteorologicoRoute: Routes = [
  {
    path: '',
    component: AvisoMeteorologicoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.avisoMeteorologico.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvisoMeteorologicoDetailComponent,
    resolve: {
      avisoMeteorologico: AvisoMeteorologicoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.avisoMeteorologico.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvisoMeteorologicoUpdateComponent,
    resolve: {
      avisoMeteorologico: AvisoMeteorologicoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.avisoMeteorologico.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvisoMeteorologicoUpdateComponent,
    resolve: {
      avisoMeteorologico: AvisoMeteorologicoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.avisoMeteorologico.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

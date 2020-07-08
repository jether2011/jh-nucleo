import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVentoVmFaixa, VentoVmFaixa } from 'app/shared/model/vento-vm-faixa.model';
import { VentoVmFaixaService } from './vento-vm-faixa.service';
import { VentoVmFaixaComponent } from './vento-vm-faixa.component';
import { VentoVmFaixaDetailComponent } from './vento-vm-faixa-detail.component';
import { VentoVmFaixaUpdateComponent } from './vento-vm-faixa-update.component';

@Injectable({ providedIn: 'root' })
export class VentoVmFaixaResolve implements Resolve<IVentoVmFaixa> {
  constructor(private service: VentoVmFaixaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVentoVmFaixa> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ventoVmFaixa: HttpResponse<VentoVmFaixa>) => {
          if (ventoVmFaixa.body) {
            return of(ventoVmFaixa.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VentoVmFaixa());
  }
}

export const ventoVmFaixaRoute: Routes = [
  {
    path: '',
    component: VentoVmFaixaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.ventoVmFaixa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VentoVmFaixaDetailComponent,
    resolve: {
      ventoVmFaixa: VentoVmFaixaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.ventoVmFaixa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VentoVmFaixaUpdateComponent,
    resolve: {
      ventoVmFaixa: VentoVmFaixaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.ventoVmFaixa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VentoVmFaixaUpdateComponent,
    resolve: {
      ventoVmFaixa: VentoVmFaixaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.ventoVmFaixa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

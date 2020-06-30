import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlanoLayer, PlanoLayer } from 'app/shared/model/plano-layer.model';
import { PlanoLayerService } from './plano-layer.service';
import { PlanoLayerComponent } from './plano-layer.component';
import { PlanoLayerDetailComponent } from './plano-layer-detail.component';
import { PlanoLayerUpdateComponent } from './plano-layer-update.component';

@Injectable({ providedIn: 'root' })
export class PlanoLayerResolve implements Resolve<IPlanoLayer> {
  constructor(private service: PlanoLayerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlanoLayer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((planoLayer: HttpResponse<PlanoLayer>) => {
          if (planoLayer.body) {
            return of(planoLayer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlanoLayer());
  }
}

export const planoLayerRoute: Routes = [
  {
    path: '',
    component: PlanoLayerComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.planoLayer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlanoLayerDetailComponent,
    resolve: {
      planoLayer: PlanoLayerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.planoLayer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlanoLayerUpdateComponent,
    resolve: {
      planoLayer: PlanoLayerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.planoLayer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlanoLayerUpdateComponent,
    resolve: {
      planoLayer: PlanoLayerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.planoLayer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

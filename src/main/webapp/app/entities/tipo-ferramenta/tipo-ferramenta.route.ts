import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoFerramenta, TipoFerramenta } from 'app/shared/model/tipo-ferramenta.model';
import { TipoFerramentaService } from './tipo-ferramenta.service';
import { TipoFerramentaComponent } from './tipo-ferramenta.component';
import { TipoFerramentaDetailComponent } from './tipo-ferramenta-detail.component';
import { TipoFerramentaUpdateComponent } from './tipo-ferramenta-update.component';

@Injectable({ providedIn: 'root' })
export class TipoFerramentaResolve implements Resolve<ITipoFerramenta> {
  constructor(private service: TipoFerramentaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoFerramenta> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoFerramenta: HttpResponse<TipoFerramenta>) => {
          if (tipoFerramenta.body) {
            return of(tipoFerramenta.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoFerramenta());
  }
}

export const tipoFerramentaRoute: Routes = [
  {
    path: '',
    component: TipoFerramentaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.tipoFerramenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TipoFerramentaDetailComponent,
    resolve: {
      tipoFerramenta: TipoFerramentaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.tipoFerramenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TipoFerramentaUpdateComponent,
    resolve: {
      tipoFerramenta: TipoFerramentaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.tipoFerramenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TipoFerramentaUpdateComponent,
    resolve: {
      tipoFerramenta: TipoFerramentaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.tipoFerramenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

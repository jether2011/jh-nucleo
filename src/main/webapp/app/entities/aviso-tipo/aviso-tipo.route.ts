import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAvisoTipo, AvisoTipo } from 'app/shared/model/aviso-tipo.model';
import { AvisoTipoService } from './aviso-tipo.service';
import { AvisoTipoComponent } from './aviso-tipo.component';
import { AvisoTipoDetailComponent } from './aviso-tipo-detail.component';
import { AvisoTipoUpdateComponent } from './aviso-tipo-update.component';

@Injectable({ providedIn: 'root' })
export class AvisoTipoResolve implements Resolve<IAvisoTipo> {
  constructor(private service: AvisoTipoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAvisoTipo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((avisoTipo: HttpResponse<AvisoTipo>) => {
          if (avisoTipo.body) {
            return of(avisoTipo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AvisoTipo());
  }
}

export const avisoTipoRoute: Routes = [
  {
    path: '',
    component: AvisoTipoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.avisoTipo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvisoTipoDetailComponent,
    resolve: {
      avisoTipo: AvisoTipoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.avisoTipo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvisoTipoUpdateComponent,
    resolve: {
      avisoTipo: AvisoTipoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.avisoTipo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvisoTipoUpdateComponent,
    resolve: {
      avisoTipo: AvisoTipoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.avisoTipo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

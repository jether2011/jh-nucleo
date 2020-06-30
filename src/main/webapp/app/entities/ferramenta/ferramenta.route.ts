import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFerramenta, Ferramenta } from 'app/shared/model/ferramenta.model';
import { FerramentaService } from './ferramenta.service';
import { FerramentaComponent } from './ferramenta.component';
import { FerramentaDetailComponent } from './ferramenta-detail.component';
import { FerramentaUpdateComponent } from './ferramenta-update.component';

@Injectable({ providedIn: 'root' })
export class FerramentaResolve implements Resolve<IFerramenta> {
  constructor(private service: FerramentaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFerramenta> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ferramenta: HttpResponse<Ferramenta>) => {
          if (ferramenta.body) {
            return of(ferramenta.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Ferramenta());
  }
}

export const ferramentaRoute: Routes = [
  {
    path: '',
    component: FerramentaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.ferramenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FerramentaDetailComponent,
    resolve: {
      ferramenta: FerramentaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.ferramenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FerramentaUpdateComponent,
    resolve: {
      ferramenta: FerramentaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.ferramenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FerramentaUpdateComponent,
    resolve: {
      ferramenta: FerramentaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.ferramenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

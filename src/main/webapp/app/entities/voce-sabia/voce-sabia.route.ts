import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVoceSabia, VoceSabia } from 'app/shared/model/voce-sabia.model';
import { VoceSabiaService } from './voce-sabia.service';
import { VoceSabiaComponent } from './voce-sabia.component';
import { VoceSabiaDetailComponent } from './voce-sabia-detail.component';
import { VoceSabiaUpdateComponent } from './voce-sabia-update.component';

@Injectable({ providedIn: 'root' })
export class VoceSabiaResolve implements Resolve<IVoceSabia> {
  constructor(private service: VoceSabiaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVoceSabia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((voceSabia: HttpResponse<VoceSabia>) => {
          if (voceSabia.body) {
            return of(voceSabia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VoceSabia());
  }
}

export const voceSabiaRoute: Routes = [
  {
    path: '',
    component: VoceSabiaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.voceSabia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VoceSabiaDetailComponent,
    resolve: {
      voceSabia: VoceSabiaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.voceSabia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VoceSabiaUpdateComponent,
    resolve: {
      voceSabia: VoceSabiaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.voceSabia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VoceSabiaUpdateComponent,
    resolve: {
      voceSabia: VoceSabiaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.voceSabia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

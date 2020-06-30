import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INoticia, Noticia } from 'app/shared/model/noticia.model';
import { NoticiaService } from './noticia.service';
import { NoticiaComponent } from './noticia.component';
import { NoticiaDetailComponent } from './noticia-detail.component';
import { NoticiaUpdateComponent } from './noticia-update.component';

@Injectable({ providedIn: 'root' })
export class NoticiaResolve implements Resolve<INoticia> {
  constructor(private service: NoticiaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INoticia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((noticia: HttpResponse<Noticia>) => {
          if (noticia.body) {
            return of(noticia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Noticia());
  }
}

export const noticiaRoute: Routes = [
  {
    path: '',
    component: NoticiaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.noticia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NoticiaDetailComponent,
    resolve: {
      noticia: NoticiaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.noticia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NoticiaUpdateComponent,
    resolve: {
      noticia: NoticiaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.noticia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NoticiaUpdateComponent,
    resolve: {
      noticia: NoticiaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.noticia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

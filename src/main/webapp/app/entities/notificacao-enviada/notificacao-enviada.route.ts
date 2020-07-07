import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INotificacaoEnviada, NotificacaoEnviada } from 'app/shared/model/notificacao-enviada.model';
import { NotificacaoEnviadaService } from './notificacao-enviada.service';
import { NotificacaoEnviadaComponent } from './notificacao-enviada.component';
import { NotificacaoEnviadaDetailComponent } from './notificacao-enviada-detail.component';
import { NotificacaoEnviadaUpdateComponent } from './notificacao-enviada-update.component';

@Injectable({ providedIn: 'root' })
export class NotificacaoEnviadaResolve implements Resolve<INotificacaoEnviada> {
  constructor(private service: NotificacaoEnviadaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INotificacaoEnviada> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((notificacaoEnviada: HttpResponse<NotificacaoEnviada>) => {
          if (notificacaoEnviada.body) {
            return of(notificacaoEnviada.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NotificacaoEnviada());
  }
}

export const notificacaoEnviadaRoute: Routes = [
  {
    path: '',
    component: NotificacaoEnviadaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.notificacaoEnviada.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NotificacaoEnviadaDetailComponent,
    resolve: {
      notificacaoEnviada: NotificacaoEnviadaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.notificacaoEnviada.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NotificacaoEnviadaUpdateComponent,
    resolve: {
      notificacaoEnviada: NotificacaoEnviadaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.notificacaoEnviada.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NotificacaoEnviadaUpdateComponent,
    resolve: {
      notificacaoEnviada: NotificacaoEnviadaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.notificacaoEnviada.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

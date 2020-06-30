import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContatoTipoEnvio, ContatoTipoEnvio } from 'app/shared/model/contato-tipo-envio.model';
import { ContatoTipoEnvioService } from './contato-tipo-envio.service';
import { ContatoTipoEnvioComponent } from './contato-tipo-envio.component';
import { ContatoTipoEnvioDetailComponent } from './contato-tipo-envio-detail.component';
import { ContatoTipoEnvioUpdateComponent } from './contato-tipo-envio-update.component';

@Injectable({ providedIn: 'root' })
export class ContatoTipoEnvioResolve implements Resolve<IContatoTipoEnvio> {
  constructor(private service: ContatoTipoEnvioService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContatoTipoEnvio> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contatoTipoEnvio: HttpResponse<ContatoTipoEnvio>) => {
          if (contatoTipoEnvio.body) {
            return of(contatoTipoEnvio.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContatoTipoEnvio());
  }
}

export const contatoTipoEnvioRoute: Routes = [
  {
    path: '',
    component: ContatoTipoEnvioComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nucleoApp.contatoTipoEnvio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContatoTipoEnvioDetailComponent,
    resolve: {
      contatoTipoEnvio: ContatoTipoEnvioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.contatoTipoEnvio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContatoTipoEnvioUpdateComponent,
    resolve: {
      contatoTipoEnvio: ContatoTipoEnvioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.contatoTipoEnvio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContatoTipoEnvioUpdateComponent,
    resolve: {
      contatoTipoEnvio: ContatoTipoEnvioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nucleoApp.contatoTipoEnvio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPerfil } from 'app/shared/model/perfil.model';

type EntityResponseType = HttpResponse<IPerfil>;
type EntityArrayResponseType = HttpResponse<IPerfil[]>;

@Injectable({ providedIn: 'root' })
export class PerfilService {
  public resourceUrl = SERVER_API_URL + 'api/perfils';

  constructor(protected http: HttpClient) {}

  create(perfil: IPerfil): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(perfil);
    return this.http
      .post<IPerfil>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(perfil: IPerfil): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(perfil);
    return this.http
      .put<IPerfil>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPerfil>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPerfil[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(perfil: IPerfil): IPerfil {
    const copy: IPerfil = Object.assign({}, perfil, {
      created: perfil.created && perfil.created.isValid() ? perfil.created.toJSON() : undefined,
      updated: perfil.updated && perfil.updated.isValid() ? perfil.updated.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.created = res.body.created ? moment(res.body.created) : undefined;
      res.body.updated = res.body.updated ? moment(res.body.updated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((perfil: IPerfil) => {
        perfil.created = perfil.created ? moment(perfil.created) : undefined;
        perfil.updated = perfil.updated ? moment(perfil.updated) : undefined;
      });
    }
    return res;
  }
}

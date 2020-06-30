import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAvisoTipo } from 'app/shared/model/aviso-tipo.model';

type EntityResponseType = HttpResponse<IAvisoTipo>;
type EntityArrayResponseType = HttpResponse<IAvisoTipo[]>;

@Injectable({ providedIn: 'root' })
export class AvisoTipoService {
  public resourceUrl = SERVER_API_URL + 'api/aviso-tipos';

  constructor(protected http: HttpClient) {}

  create(avisoTipo: IAvisoTipo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(avisoTipo);
    return this.http
      .post<IAvisoTipo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(avisoTipo: IAvisoTipo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(avisoTipo);
    return this.http
      .put<IAvisoTipo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAvisoTipo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAvisoTipo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(avisoTipo: IAvisoTipo): IAvisoTipo {
    const copy: IAvisoTipo = Object.assign({}, avisoTipo, {
      created: avisoTipo.created && avisoTipo.created.isValid() ? avisoTipo.created.toJSON() : undefined,
      updated: avisoTipo.updated && avisoTipo.updated.isValid() ? avisoTipo.updated.toJSON() : undefined,
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
      res.body.forEach((avisoTipo: IAvisoTipo) => {
        avisoTipo.created = avisoTipo.created ? moment(avisoTipo.created) : undefined;
        avisoTipo.updated = avisoTipo.updated ? moment(avisoTipo.updated) : undefined;
      });
    }
    return res;
  }
}

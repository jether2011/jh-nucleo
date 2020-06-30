import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRecursoTipo } from 'app/shared/model/recurso-tipo.model';

type EntityResponseType = HttpResponse<IRecursoTipo>;
type EntityArrayResponseType = HttpResponse<IRecursoTipo[]>;

@Injectable({ providedIn: 'root' })
export class RecursoTipoService {
  public resourceUrl = SERVER_API_URL + 'api/recurso-tipos';

  constructor(protected http: HttpClient) {}

  create(recursoTipo: IRecursoTipo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recursoTipo);
    return this.http
      .post<IRecursoTipo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(recursoTipo: IRecursoTipo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recursoTipo);
    return this.http
      .put<IRecursoTipo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRecursoTipo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRecursoTipo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(recursoTipo: IRecursoTipo): IRecursoTipo {
    const copy: IRecursoTipo = Object.assign({}, recursoTipo, {
      created: recursoTipo.created && recursoTipo.created.isValid() ? recursoTipo.created.toJSON() : undefined,
      updated: recursoTipo.updated && recursoTipo.updated.isValid() ? recursoTipo.updated.toJSON() : undefined,
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
      res.body.forEach((recursoTipo: IRecursoTipo) => {
        recursoTipo.created = recursoTipo.created ? moment(recursoTipo.created) : undefined;
        recursoTipo.updated = recursoTipo.updated ? moment(recursoTipo.updated) : undefined;
      });
    }
    return res;
  }
}

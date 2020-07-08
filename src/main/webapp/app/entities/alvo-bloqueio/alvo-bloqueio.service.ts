import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAlvoBloqueio } from 'app/shared/model/alvo-bloqueio.model';

type EntityResponseType = HttpResponse<IAlvoBloqueio>;
type EntityArrayResponseType = HttpResponse<IAlvoBloqueio[]>;

@Injectable({ providedIn: 'root' })
export class AlvoBloqueioService {
  public resourceUrl = SERVER_API_URL + 'api/alvo-bloqueios';

  constructor(protected http: HttpClient) {}

  create(alvoBloqueio: IAlvoBloqueio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alvoBloqueio);
    return this.http
      .post<IAlvoBloqueio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(alvoBloqueio: IAlvoBloqueio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alvoBloqueio);
    return this.http
      .put<IAlvoBloqueio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAlvoBloqueio>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAlvoBloqueio[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(alvoBloqueio: IAlvoBloqueio): IAlvoBloqueio {
    const copy: IAlvoBloqueio = Object.assign({}, alvoBloqueio, {
      created: alvoBloqueio.created && alvoBloqueio.created.isValid() ? alvoBloqueio.created.toJSON() : undefined,
      updated: alvoBloqueio.updated && alvoBloqueio.updated.isValid() ? alvoBloqueio.updated.toJSON() : undefined,
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
      res.body.forEach((alvoBloqueio: IAlvoBloqueio) => {
        alvoBloqueio.created = alvoBloqueio.created ? moment(alvoBloqueio.created) : undefined;
        alvoBloqueio.updated = alvoBloqueio.updated ? moment(alvoBloqueio.updated) : undefined;
      });
    }
    return res;
  }
}

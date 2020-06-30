import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAviso } from 'app/shared/model/aviso.model';

type EntityResponseType = HttpResponse<IAviso>;
type EntityArrayResponseType = HttpResponse<IAviso[]>;

@Injectable({ providedIn: 'root' })
export class AvisoService {
  public resourceUrl = SERVER_API_URL + 'api/avisos';

  constructor(protected http: HttpClient) {}

  create(aviso: IAviso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aviso);
    return this.http
      .post<IAviso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(aviso: IAviso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aviso);
    return this.http
      .put<IAviso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAviso>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAviso[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(aviso: IAviso): IAviso {
    const copy: IAviso = Object.assign({}, aviso, {
      created: aviso.created && aviso.created.isValid() ? aviso.created.toJSON() : undefined,
      updated: aviso.updated && aviso.updated.isValid() ? aviso.updated.toJSON() : undefined,
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
      res.body.forEach((aviso: IAviso) => {
        aviso.created = aviso.created ? moment(aviso.created) : undefined;
        aviso.updated = aviso.updated ? moment(aviso.updated) : undefined;
      });
    }
    return res;
  }
}

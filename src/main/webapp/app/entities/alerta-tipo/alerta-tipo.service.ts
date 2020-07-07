import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAlertaTipo } from 'app/shared/model/alerta-tipo.model';

type EntityResponseType = HttpResponse<IAlertaTipo>;
type EntityArrayResponseType = HttpResponse<IAlertaTipo[]>;

@Injectable({ providedIn: 'root' })
export class AlertaTipoService {
  public resourceUrl = SERVER_API_URL + 'api/alerta-tipos';

  constructor(protected http: HttpClient) {}

  create(alertaTipo: IAlertaTipo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alertaTipo);
    return this.http
      .post<IAlertaTipo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(alertaTipo: IAlertaTipo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alertaTipo);
    return this.http
      .put<IAlertaTipo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAlertaTipo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAlertaTipo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(alertaTipo: IAlertaTipo): IAlertaTipo {
    const copy: IAlertaTipo = Object.assign({}, alertaTipo, {
      created: alertaTipo.created && alertaTipo.created.isValid() ? alertaTipo.created.toJSON() : undefined,
      updated: alertaTipo.updated && alertaTipo.updated.isValid() ? alertaTipo.updated.toJSON() : undefined,
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
      res.body.forEach((alertaTipo: IAlertaTipo) => {
        alertaTipo.created = alertaTipo.created ? moment(alertaTipo.created) : undefined;
        alertaTipo.updated = alertaTipo.updated ? moment(alertaTipo.updated) : undefined;
      });
    }
    return res;
  }
}

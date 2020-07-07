import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAlerta } from 'app/shared/model/alerta.model';

type EntityResponseType = HttpResponse<IAlerta>;
type EntityArrayResponseType = HttpResponse<IAlerta[]>;

@Injectable({ providedIn: 'root' })
export class AlertaService {
  public resourceUrl = SERVER_API_URL + 'api/alertas';

  constructor(protected http: HttpClient) {}

  create(alerta: IAlerta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alerta);
    return this.http
      .post<IAlerta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(alerta: IAlerta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alerta);
    return this.http
      .put<IAlerta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAlerta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAlerta[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(alerta: IAlerta): IAlerta {
    const copy: IAlerta = Object.assign({}, alerta, {
      created: alerta.created && alerta.created.isValid() ? alerta.created.toJSON() : undefined,
      updated: alerta.updated && alerta.updated.isValid() ? alerta.updated.toJSON() : undefined,
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
      res.body.forEach((alerta: IAlerta) => {
        alerta.created = alerta.created ? moment(alerta.created) : undefined;
        alerta.updated = alerta.updated ? moment(alerta.updated) : undefined;
      });
    }
    return res;
  }
}

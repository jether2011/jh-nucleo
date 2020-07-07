import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAlertaRisco } from 'app/shared/model/alerta-risco.model';

type EntityResponseType = HttpResponse<IAlertaRisco>;
type EntityArrayResponseType = HttpResponse<IAlertaRisco[]>;

@Injectable({ providedIn: 'root' })
export class AlertaRiscoService {
  public resourceUrl = SERVER_API_URL + 'api/alerta-riscos';

  constructor(protected http: HttpClient) {}

  create(alertaRisco: IAlertaRisco): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alertaRisco);
    return this.http
      .post<IAlertaRisco>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(alertaRisco: IAlertaRisco): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alertaRisco);
    return this.http
      .put<IAlertaRisco>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAlertaRisco>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAlertaRisco[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(alertaRisco: IAlertaRisco): IAlertaRisco {
    const copy: IAlertaRisco = Object.assign({}, alertaRisco, {
      created: alertaRisco.created && alertaRisco.created.isValid() ? alertaRisco.created.toJSON() : undefined,
      updated: alertaRisco.updated && alertaRisco.updated.isValid() ? alertaRisco.updated.toJSON() : undefined,
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
      res.body.forEach((alertaRisco: IAlertaRisco) => {
        alertaRisco.created = alertaRisco.created ? moment(alertaRisco.created) : undefined;
        alertaRisco.updated = alertaRisco.updated ? moment(alertaRisco.updated) : undefined;
      });
    }
    return res;
  }
}

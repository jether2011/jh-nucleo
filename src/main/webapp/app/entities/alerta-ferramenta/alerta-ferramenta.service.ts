import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAlertaFerramenta } from 'app/shared/model/alerta-ferramenta.model';

type EntityResponseType = HttpResponse<IAlertaFerramenta>;
type EntityArrayResponseType = HttpResponse<IAlertaFerramenta[]>;

@Injectable({ providedIn: 'root' })
export class AlertaFerramentaService {
  public resourceUrl = SERVER_API_URL + 'api/alerta-ferramentas';

  constructor(protected http: HttpClient) {}

  create(alertaFerramenta: IAlertaFerramenta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alertaFerramenta);
    return this.http
      .post<IAlertaFerramenta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(alertaFerramenta: IAlertaFerramenta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alertaFerramenta);
    return this.http
      .put<IAlertaFerramenta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAlertaFerramenta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAlertaFerramenta[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(alertaFerramenta: IAlertaFerramenta): IAlertaFerramenta {
    const copy: IAlertaFerramenta = Object.assign({}, alertaFerramenta, {
      created: alertaFerramenta.created && alertaFerramenta.created.isValid() ? alertaFerramenta.created.toJSON() : undefined,
      updated: alertaFerramenta.updated && alertaFerramenta.updated.isValid() ? alertaFerramenta.updated.toJSON() : undefined,
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
      res.body.forEach((alertaFerramenta: IAlertaFerramenta) => {
        alertaFerramenta.created = alertaFerramenta.created ? moment(alertaFerramenta.created) : undefined;
        alertaFerramenta.updated = alertaFerramenta.updated ? moment(alertaFerramenta.updated) : undefined;
      });
    }
    return res;
  }
}

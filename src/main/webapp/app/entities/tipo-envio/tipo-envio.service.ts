import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoEnvio } from 'app/shared/model/tipo-envio.model';

type EntityResponseType = HttpResponse<ITipoEnvio>;
type EntityArrayResponseType = HttpResponse<ITipoEnvio[]>;

@Injectable({ providedIn: 'root' })
export class TipoEnvioService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-envios';

  constructor(protected http: HttpClient) {}

  create(tipoEnvio: ITipoEnvio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tipoEnvio);
    return this.http
      .post<ITipoEnvio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tipoEnvio: ITipoEnvio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tipoEnvio);
    return this.http
      .put<ITipoEnvio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITipoEnvio>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITipoEnvio[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tipoEnvio: ITipoEnvio): ITipoEnvio {
    const copy: ITipoEnvio = Object.assign({}, tipoEnvio, {
      created: tipoEnvio.created && tipoEnvio.created.isValid() ? tipoEnvio.created.toJSON() : undefined,
      updated: tipoEnvio.updated && tipoEnvio.updated.isValid() ? tipoEnvio.updated.toJSON() : undefined,
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
      res.body.forEach((tipoEnvio: ITipoEnvio) => {
        tipoEnvio.created = tipoEnvio.created ? moment(tipoEnvio.created) : undefined;
        tipoEnvio.updated = tipoEnvio.updated ? moment(tipoEnvio.updated) : undefined;
      });
    }
    return res;
  }
}

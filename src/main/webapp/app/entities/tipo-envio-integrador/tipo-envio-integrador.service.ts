import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoEnvioIntegrador } from 'app/shared/model/tipo-envio-integrador.model';

type EntityResponseType = HttpResponse<ITipoEnvioIntegrador>;
type EntityArrayResponseType = HttpResponse<ITipoEnvioIntegrador[]>;

@Injectable({ providedIn: 'root' })
export class TipoEnvioIntegradorService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-envio-integradors';

  constructor(protected http: HttpClient) {}

  create(tipoEnvioIntegrador: ITipoEnvioIntegrador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tipoEnvioIntegrador);
    return this.http
      .post<ITipoEnvioIntegrador>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tipoEnvioIntegrador: ITipoEnvioIntegrador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tipoEnvioIntegrador);
    return this.http
      .put<ITipoEnvioIntegrador>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITipoEnvioIntegrador>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITipoEnvioIntegrador[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tipoEnvioIntegrador: ITipoEnvioIntegrador): ITipoEnvioIntegrador {
    const copy: ITipoEnvioIntegrador = Object.assign({}, tipoEnvioIntegrador, {
      created: tipoEnvioIntegrador.created && tipoEnvioIntegrador.created.isValid() ? tipoEnvioIntegrador.created.toJSON() : undefined,
      updated: tipoEnvioIntegrador.updated && tipoEnvioIntegrador.updated.isValid() ? tipoEnvioIntegrador.updated.toJSON() : undefined,
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
      res.body.forEach((tipoEnvioIntegrador: ITipoEnvioIntegrador) => {
        tipoEnvioIntegrador.created = tipoEnvioIntegrador.created ? moment(tipoEnvioIntegrador.created) : undefined;
        tipoEnvioIntegrador.updated = tipoEnvioIntegrador.updated ? moment(tipoEnvioIntegrador.updated) : undefined;
      });
    }
    return res;
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAvisoMeteorologico } from 'app/shared/model/aviso-meteorologico.model';

type EntityResponseType = HttpResponse<IAvisoMeteorologico>;
type EntityArrayResponseType = HttpResponse<IAvisoMeteorologico[]>;

@Injectable({ providedIn: 'root' })
export class AvisoMeteorologicoService {
  public resourceUrl = SERVER_API_URL + 'api/aviso-meteorologicos';

  constructor(protected http: HttpClient) {}

  create(avisoMeteorologico: IAvisoMeteorologico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(avisoMeteorologico);
    return this.http
      .post<IAvisoMeteorologico>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(avisoMeteorologico: IAvisoMeteorologico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(avisoMeteorologico);
    return this.http
      .put<IAvisoMeteorologico>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAvisoMeteorologico>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAvisoMeteorologico[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(avisoMeteorologico: IAvisoMeteorologico): IAvisoMeteorologico {
    const copy: IAvisoMeteorologico = Object.assign({}, avisoMeteorologico, {
      inicio: avisoMeteorologico.inicio && avisoMeteorologico.inicio.isValid() ? avisoMeteorologico.inicio.toJSON() : undefined,
      fim: avisoMeteorologico.fim && avisoMeteorologico.fim.isValid() ? avisoMeteorologico.fim.toJSON() : undefined,
      created: avisoMeteorologico.created && avisoMeteorologico.created.isValid() ? avisoMeteorologico.created.toJSON() : undefined,
      updated: avisoMeteorologico.updated && avisoMeteorologico.updated.isValid() ? avisoMeteorologico.updated.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.inicio = res.body.inicio ? moment(res.body.inicio) : undefined;
      res.body.fim = res.body.fim ? moment(res.body.fim) : undefined;
      res.body.created = res.body.created ? moment(res.body.created) : undefined;
      res.body.updated = res.body.updated ? moment(res.body.updated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((avisoMeteorologico: IAvisoMeteorologico) => {
        avisoMeteorologico.inicio = avisoMeteorologico.inicio ? moment(avisoMeteorologico.inicio) : undefined;
        avisoMeteorologico.fim = avisoMeteorologico.fim ? moment(avisoMeteorologico.fim) : undefined;
        avisoMeteorologico.created = avisoMeteorologico.created ? moment(avisoMeteorologico.created) : undefined;
        avisoMeteorologico.updated = avisoMeteorologico.updated ? moment(avisoMeteorologico.updated) : undefined;
      });
    }
    return res;
  }
}

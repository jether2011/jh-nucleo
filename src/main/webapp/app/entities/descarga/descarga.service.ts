import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDescarga } from 'app/shared/model/descarga.model';

type EntityResponseType = HttpResponse<IDescarga>;
type EntityArrayResponseType = HttpResponse<IDescarga[]>;

@Injectable({ providedIn: 'root' })
export class DescargaService {
  public resourceUrl = SERVER_API_URL + 'api/descargas';

  constructor(protected http: HttpClient) {}

  create(descarga: IDescarga): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(descarga);
    return this.http
      .post<IDescarga>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(descarga: IDescarga): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(descarga);
    return this.http
      .put<IDescarga>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDescarga>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDescarga[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(descarga: IDescarga): IDescarga {
    const copy: IDescarga = Object.assign({}, descarga, {
      dataPrimeiraDescarga:
        descarga.dataPrimeiraDescarga && descarga.dataPrimeiraDescarga.isValid() ? descarga.dataPrimeiraDescarga.toJSON() : undefined,
      created: descarga.created && descarga.created.isValid() ? descarga.created.toJSON() : undefined,
      updated: descarga.updated && descarga.updated.isValid() ? descarga.updated.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataPrimeiraDescarga = res.body.dataPrimeiraDescarga ? moment(res.body.dataPrimeiraDescarga) : undefined;
      res.body.created = res.body.created ? moment(res.body.created) : undefined;
      res.body.updated = res.body.updated ? moment(res.body.updated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((descarga: IDescarga) => {
        descarga.dataPrimeiraDescarga = descarga.dataPrimeiraDescarga ? moment(descarga.dataPrimeiraDescarga) : undefined;
        descarga.created = descarga.created ? moment(descarga.created) : undefined;
        descarga.updated = descarga.updated ? moment(descarga.updated) : undefined;
      });
    }
    return res;
  }
}

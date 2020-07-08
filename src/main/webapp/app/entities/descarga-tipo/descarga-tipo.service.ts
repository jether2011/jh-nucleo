import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDescargaTipo } from 'app/shared/model/descarga-tipo.model';

type EntityResponseType = HttpResponse<IDescargaTipo>;
type EntityArrayResponseType = HttpResponse<IDescargaTipo[]>;

@Injectable({ providedIn: 'root' })
export class DescargaTipoService {
  public resourceUrl = SERVER_API_URL + 'api/descarga-tipos';

  constructor(protected http: HttpClient) {}

  create(descargaTipo: IDescargaTipo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(descargaTipo);
    return this.http
      .post<IDescargaTipo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(descargaTipo: IDescargaTipo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(descargaTipo);
    return this.http
      .put<IDescargaTipo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDescargaTipo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDescargaTipo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(descargaTipo: IDescargaTipo): IDescargaTipo {
    const copy: IDescargaTipo = Object.assign({}, descargaTipo, {
      created: descargaTipo.created && descargaTipo.created.isValid() ? descargaTipo.created.toJSON() : undefined,
      updated: descargaTipo.updated && descargaTipo.updated.isValid() ? descargaTipo.updated.toJSON() : undefined,
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
      res.body.forEach((descargaTipo: IDescargaTipo) => {
        descargaTipo.created = descargaTipo.created ? moment(descargaTipo.created) : undefined;
        descargaTipo.updated = descargaTipo.updated ? moment(descargaTipo.updated) : undefined;
      });
    }
    return res;
  }
}

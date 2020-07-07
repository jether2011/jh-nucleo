import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDiaSemana } from 'app/shared/model/dia-semana.model';

type EntityResponseType = HttpResponse<IDiaSemana>;
type EntityArrayResponseType = HttpResponse<IDiaSemana[]>;

@Injectable({ providedIn: 'root' })
export class DiaSemanaService {
  public resourceUrl = SERVER_API_URL + 'api/dia-semanas';

  constructor(protected http: HttpClient) {}

  create(diaSemana: IDiaSemana): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(diaSemana);
    return this.http
      .post<IDiaSemana>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(diaSemana: IDiaSemana): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(diaSemana);
    return this.http
      .put<IDiaSemana>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDiaSemana>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDiaSemana[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(diaSemana: IDiaSemana): IDiaSemana {
    const copy: IDiaSemana = Object.assign({}, diaSemana, {
      created: diaSemana.created && diaSemana.created.isValid() ? diaSemana.created.toJSON() : undefined,
      updated: diaSemana.updated && diaSemana.updated.isValid() ? diaSemana.updated.toJSON() : undefined,
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
      res.body.forEach((diaSemana: IDiaSemana) => {
        diaSemana.created = diaSemana.created ? moment(diaSemana.created) : undefined;
        diaSemana.updated = diaSemana.updated ? moment(diaSemana.updated) : undefined;
      });
    }
    return res;
  }
}

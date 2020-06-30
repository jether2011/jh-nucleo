import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJornadaTrabalho } from 'app/shared/model/jornada-trabalho.model';

type EntityResponseType = HttpResponse<IJornadaTrabalho>;
type EntityArrayResponseType = HttpResponse<IJornadaTrabalho[]>;

@Injectable({ providedIn: 'root' })
export class JornadaTrabalhoService {
  public resourceUrl = SERVER_API_URL + 'api/jornada-trabalhos';

  constructor(protected http: HttpClient) {}

  create(jornadaTrabalho: IJornadaTrabalho): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jornadaTrabalho);
    return this.http
      .post<IJornadaTrabalho>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jornadaTrabalho: IJornadaTrabalho): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jornadaTrabalho);
    return this.http
      .put<IJornadaTrabalho>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJornadaTrabalho>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJornadaTrabalho[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(jornadaTrabalho: IJornadaTrabalho): IJornadaTrabalho {
    const copy: IJornadaTrabalho = Object.assign({}, jornadaTrabalho, {
      created: jornadaTrabalho.created && jornadaTrabalho.created.isValid() ? jornadaTrabalho.created.toJSON() : undefined,
      updated: jornadaTrabalho.updated && jornadaTrabalho.updated.isValid() ? jornadaTrabalho.updated.toJSON() : undefined,
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
      res.body.forEach((jornadaTrabalho: IJornadaTrabalho) => {
        jornadaTrabalho.created = jornadaTrabalho.created ? moment(jornadaTrabalho.created) : undefined;
        jornadaTrabalho.updated = jornadaTrabalho.updated ? moment(jornadaTrabalho.updated) : undefined;
      });
    }
    return res;
  }
}

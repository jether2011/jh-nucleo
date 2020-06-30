import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICondicaoTempo } from 'app/shared/model/condicao-tempo.model';

type EntityResponseType = HttpResponse<ICondicaoTempo>;
type EntityArrayResponseType = HttpResponse<ICondicaoTempo[]>;

@Injectable({ providedIn: 'root' })
export class CondicaoTempoService {
  public resourceUrl = SERVER_API_URL + 'api/condicao-tempos';

  constructor(protected http: HttpClient) {}

  create(condicaoTempo: ICondicaoTempo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(condicaoTempo);
    return this.http
      .post<ICondicaoTempo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(condicaoTempo: ICondicaoTempo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(condicaoTempo);
    return this.http
      .put<ICondicaoTempo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICondicaoTempo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICondicaoTempo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(condicaoTempo: ICondicaoTempo): ICondicaoTempo {
    const copy: ICondicaoTempo = Object.assign({}, condicaoTempo, {
      created: condicaoTempo.created && condicaoTempo.created.isValid() ? condicaoTempo.created.toJSON() : undefined,
      updated: condicaoTempo.updated && condicaoTempo.updated.isValid() ? condicaoTempo.updated.toJSON() : undefined,
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
      res.body.forEach((condicaoTempo: ICondicaoTempo) => {
        condicaoTempo.created = condicaoTempo.created ? moment(condicaoTempo.created) : undefined;
        condicaoTempo.updated = condicaoTempo.updated ? moment(condicaoTempo.updated) : undefined;
      });
    }
    return res;
  }
}

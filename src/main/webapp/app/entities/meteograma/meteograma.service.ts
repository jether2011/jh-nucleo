import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMeteograma } from 'app/shared/model/meteograma.model';

type EntityResponseType = HttpResponse<IMeteograma>;
type EntityArrayResponseType = HttpResponse<IMeteograma[]>;

@Injectable({ providedIn: 'root' })
export class MeteogramaService {
  public resourceUrl = SERVER_API_URL + 'api/meteogramas';

  constructor(protected http: HttpClient) {}

  create(meteograma: IMeteograma): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(meteograma);
    return this.http
      .post<IMeteograma>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(meteograma: IMeteograma): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(meteograma);
    return this.http
      .put<IMeteograma>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMeteograma>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMeteograma[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(meteograma: IMeteograma): IMeteograma {
    const copy: IMeteograma = Object.assign({}, meteograma, {
      created: meteograma.created && meteograma.created.isValid() ? meteograma.created.toJSON() : undefined,
      updated: meteograma.updated && meteograma.updated.isValid() ? meteograma.updated.toJSON() : undefined,
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
      res.body.forEach((meteograma: IMeteograma) => {
        meteograma.created = meteograma.created ? moment(meteograma.created) : undefined;
        meteograma.updated = meteograma.updated ? moment(meteograma.updated) : undefined;
      });
    }
    return res;
  }
}

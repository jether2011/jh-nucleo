import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRede } from 'app/shared/model/rede.model';

type EntityResponseType = HttpResponse<IRede>;
type EntityArrayResponseType = HttpResponse<IRede[]>;

@Injectable({ providedIn: 'root' })
export class RedeService {
  public resourceUrl = SERVER_API_URL + 'api/redes';

  constructor(protected http: HttpClient) {}

  create(rede: IRede): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rede);
    return this.http
      .post<IRede>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(rede: IRede): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rede);
    return this.http
      .put<IRede>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRede>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRede[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(rede: IRede): IRede {
    const copy: IRede = Object.assign({}, rede, {
      created: rede.created && rede.created.isValid() ? rede.created.toJSON() : undefined,
      updated: rede.updated && rede.updated.isValid() ? rede.updated.toJSON() : undefined,
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
      res.body.forEach((rede: IRede) => {
        rede.created = rede.created ? moment(rede.created) : undefined;
        rede.updated = rede.updated ? moment(rede.updated) : undefined;
      });
    }
    return res;
  }
}

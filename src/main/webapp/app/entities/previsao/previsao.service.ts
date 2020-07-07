import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPrevisao } from 'app/shared/model/previsao.model';

type EntityResponseType = HttpResponse<IPrevisao>;
type EntityArrayResponseType = HttpResponse<IPrevisao[]>;

@Injectable({ providedIn: 'root' })
export class PrevisaoService {
  public resourceUrl = SERVER_API_URL + 'api/previsaos';

  constructor(protected http: HttpClient) {}

  create(previsao: IPrevisao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(previsao);
    return this.http
      .post<IPrevisao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(previsao: IPrevisao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(previsao);
    return this.http
      .put<IPrevisao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPrevisao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPrevisao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(previsao: IPrevisao): IPrevisao {
    const copy: IPrevisao = Object.assign({}, previsao, {
      created: previsao.created && previsao.created.isValid() ? previsao.created.toJSON() : undefined,
      updated: previsao.updated && previsao.updated.isValid() ? previsao.updated.toJSON() : undefined,
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
      res.body.forEach((previsao: IPrevisao) => {
        previsao.created = previsao.created ? moment(previsao.created) : undefined;
        previsao.updated = previsao.updated ? moment(previsao.updated) : undefined;
      });
    }
    return res;
  }
}

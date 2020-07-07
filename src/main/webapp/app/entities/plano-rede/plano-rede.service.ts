import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlanoRede } from 'app/shared/model/plano-rede.model';

type EntityResponseType = HttpResponse<IPlanoRede>;
type EntityArrayResponseType = HttpResponse<IPlanoRede[]>;

@Injectable({ providedIn: 'root' })
export class PlanoRedeService {
  public resourceUrl = SERVER_API_URL + 'api/plano-redes';

  constructor(protected http: HttpClient) {}

  create(planoRede: IPlanoRede): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(planoRede);
    return this.http
      .post<IPlanoRede>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(planoRede: IPlanoRede): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(planoRede);
    return this.http
      .put<IPlanoRede>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlanoRede>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlanoRede[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(planoRede: IPlanoRede): IPlanoRede {
    const copy: IPlanoRede = Object.assign({}, planoRede, {
      created: planoRede.created && planoRede.created.isValid() ? planoRede.created.toJSON() : undefined,
      updated: planoRede.updated && planoRede.updated.isValid() ? planoRede.updated.toJSON() : undefined,
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
      res.body.forEach((planoRede: IPlanoRede) => {
        planoRede.created = planoRede.created ? moment(planoRede.created) : undefined;
        planoRede.updated = planoRede.updated ? moment(planoRede.updated) : undefined;
      });
    }
    return res;
  }
}

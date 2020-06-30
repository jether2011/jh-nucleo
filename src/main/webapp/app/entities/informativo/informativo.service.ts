import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInformativo } from 'app/shared/model/informativo.model';

type EntityResponseType = HttpResponse<IInformativo>;
type EntityArrayResponseType = HttpResponse<IInformativo[]>;

@Injectable({ providedIn: 'root' })
export class InformativoService {
  public resourceUrl = SERVER_API_URL + 'api/informativos';

  constructor(protected http: HttpClient) {}

  create(informativo: IInformativo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(informativo);
    return this.http
      .post<IInformativo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(informativo: IInformativo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(informativo);
    return this.http
      .put<IInformativo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInformativo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInformativo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(informativo: IInformativo): IInformativo {
    const copy: IInformativo = Object.assign({}, informativo, {
      created: informativo.created && informativo.created.isValid() ? informativo.created.toJSON() : undefined,
      updated: informativo.updated && informativo.updated.isValid() ? informativo.updated.toJSON() : undefined,
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
      res.body.forEach((informativo: IInformativo) => {
        informativo.created = informativo.created ? moment(informativo.created) : undefined;
        informativo.updated = informativo.updated ? moment(informativo.updated) : undefined;
      });
    }
    return res;
  }
}

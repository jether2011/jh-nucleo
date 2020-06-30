import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConsolidacao } from 'app/shared/model/consolidacao.model';

type EntityResponseType = HttpResponse<IConsolidacao>;
type EntityArrayResponseType = HttpResponse<IConsolidacao[]>;

@Injectable({ providedIn: 'root' })
export class ConsolidacaoService {
  public resourceUrl = SERVER_API_URL + 'api/consolidacaos';

  constructor(protected http: HttpClient) {}

  create(consolidacao: IConsolidacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(consolidacao);
    return this.http
      .post<IConsolidacao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(consolidacao: IConsolidacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(consolidacao);
    return this.http
      .put<IConsolidacao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IConsolidacao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IConsolidacao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(consolidacao: IConsolidacao): IConsolidacao {
    const copy: IConsolidacao = Object.assign({}, consolidacao, {
      data: consolidacao.data && consolidacao.data.isValid() ? consolidacao.data.format(DATE_FORMAT) : undefined,
      created: consolidacao.created && consolidacao.created.isValid() ? consolidacao.created.toJSON() : undefined,
      updated: consolidacao.updated && consolidacao.updated.isValid() ? consolidacao.updated.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data ? moment(res.body.data) : undefined;
      res.body.created = res.body.created ? moment(res.body.created) : undefined;
      res.body.updated = res.body.updated ? moment(res.body.updated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((consolidacao: IConsolidacao) => {
        consolidacao.data = consolidacao.data ? moment(consolidacao.data) : undefined;
        consolidacao.created = consolidacao.created ? moment(consolidacao.created) : undefined;
        consolidacao.updated = consolidacao.updated ? moment(consolidacao.updated) : undefined;
      });
    }
    return res;
  }
}

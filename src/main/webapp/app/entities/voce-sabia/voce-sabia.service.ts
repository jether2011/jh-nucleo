import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVoceSabia } from 'app/shared/model/voce-sabia.model';

type EntityResponseType = HttpResponse<IVoceSabia>;
type EntityArrayResponseType = HttpResponse<IVoceSabia[]>;

@Injectable({ providedIn: 'root' })
export class VoceSabiaService {
  public resourceUrl = SERVER_API_URL + 'api/voce-sabias';

  constructor(protected http: HttpClient) {}

  create(voceSabia: IVoceSabia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voceSabia);
    return this.http
      .post<IVoceSabia>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(voceSabia: IVoceSabia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voceSabia);
    return this.http
      .put<IVoceSabia>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVoceSabia>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVoceSabia[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(voceSabia: IVoceSabia): IVoceSabia {
    const copy: IVoceSabia = Object.assign({}, voceSabia, {
      created: voceSabia.created && voceSabia.created.isValid() ? voceSabia.created.toJSON() : undefined,
      updated: voceSabia.updated && voceSabia.updated.isValid() ? voceSabia.updated.toJSON() : undefined,
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
      res.body.forEach((voceSabia: IVoceSabia) => {
        voceSabia.created = voceSabia.created ? moment(voceSabia.created) : undefined;
        voceSabia.updated = voceSabia.updated ? moment(voceSabia.updated) : undefined;
      });
    }
    return res;
  }
}

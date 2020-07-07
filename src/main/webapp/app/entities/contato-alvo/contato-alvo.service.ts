import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContatoAlvo } from 'app/shared/model/contato-alvo.model';

type EntityResponseType = HttpResponse<IContatoAlvo>;
type EntityArrayResponseType = HttpResponse<IContatoAlvo[]>;

@Injectable({ providedIn: 'root' })
export class ContatoAlvoService {
  public resourceUrl = SERVER_API_URL + 'api/contato-alvos';

  constructor(protected http: HttpClient) {}

  create(contatoAlvo: IContatoAlvo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contatoAlvo);
    return this.http
      .post<IContatoAlvo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(contatoAlvo: IContatoAlvo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contatoAlvo);
    return this.http
      .put<IContatoAlvo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IContatoAlvo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContatoAlvo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(contatoAlvo: IContatoAlvo): IContatoAlvo {
    const copy: IContatoAlvo = Object.assign({}, contatoAlvo, {
      created: contatoAlvo.created && contatoAlvo.created.isValid() ? contatoAlvo.created.toJSON() : undefined,
      updated: contatoAlvo.updated && contatoAlvo.updated.isValid() ? contatoAlvo.updated.toJSON() : undefined,
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
      res.body.forEach((contatoAlvo: IContatoAlvo) => {
        contatoAlvo.created = contatoAlvo.created ? moment(contatoAlvo.created) : undefined;
        contatoAlvo.updated = contatoAlvo.updated ? moment(contatoAlvo.updated) : undefined;
      });
    }
    return res;
  }
}

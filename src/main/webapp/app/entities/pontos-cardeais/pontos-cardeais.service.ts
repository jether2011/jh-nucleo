import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPontosCardeais } from 'app/shared/model/pontos-cardeais.model';

type EntityResponseType = HttpResponse<IPontosCardeais>;
type EntityArrayResponseType = HttpResponse<IPontosCardeais[]>;

@Injectable({ providedIn: 'root' })
export class PontosCardeaisService {
  public resourceUrl = SERVER_API_URL + 'api/pontos-cardeais';

  constructor(protected http: HttpClient) {}

  create(pontosCardeais: IPontosCardeais): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pontosCardeais);
    return this.http
      .post<IPontosCardeais>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pontosCardeais: IPontosCardeais): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pontosCardeais);
    return this.http
      .put<IPontosCardeais>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPontosCardeais>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPontosCardeais[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pontosCardeais: IPontosCardeais): IPontosCardeais {
    const copy: IPontosCardeais = Object.assign({}, pontosCardeais, {
      created: pontosCardeais.created && pontosCardeais.created.isValid() ? pontosCardeais.created.toJSON() : undefined,
      updated: pontosCardeais.updated && pontosCardeais.updated.isValid() ? pontosCardeais.updated.toJSON() : undefined,
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
      res.body.forEach((pontosCardeais: IPontosCardeais) => {
        pontosCardeais.created = pontosCardeais.created ? moment(pontosCardeais.created) : undefined;
        pontosCardeais.updated = pontosCardeais.updated ? moment(pontosCardeais.updated) : undefined;
      });
    }
    return res;
  }
}

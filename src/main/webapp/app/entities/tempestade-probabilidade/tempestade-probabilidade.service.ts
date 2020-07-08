import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITempestadeProbabilidade } from 'app/shared/model/tempestade-probabilidade.model';

type EntityResponseType = HttpResponse<ITempestadeProbabilidade>;
type EntityArrayResponseType = HttpResponse<ITempestadeProbabilidade[]>;

@Injectable({ providedIn: 'root' })
export class TempestadeProbabilidadeService {
  public resourceUrl = SERVER_API_URL + 'api/tempestade-probabilidades';

  constructor(protected http: HttpClient) {}

  create(tempestadeProbabilidade: ITempestadeProbabilidade): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tempestadeProbabilidade);
    return this.http
      .post<ITempestadeProbabilidade>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tempestadeProbabilidade: ITempestadeProbabilidade): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tempestadeProbabilidade);
    return this.http
      .put<ITempestadeProbabilidade>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITempestadeProbabilidade>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITempestadeProbabilidade[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tempestadeProbabilidade: ITempestadeProbabilidade): ITempestadeProbabilidade {
    const copy: ITempestadeProbabilidade = Object.assign({}, tempestadeProbabilidade, {
      created:
        tempestadeProbabilidade.created && tempestadeProbabilidade.created.isValid() ? tempestadeProbabilidade.created.toJSON() : undefined,
      updated:
        tempestadeProbabilidade.updated && tempestadeProbabilidade.updated.isValid() ? tempestadeProbabilidade.updated.toJSON() : undefined,
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
      res.body.forEach((tempestadeProbabilidade: ITempestadeProbabilidade) => {
        tempestadeProbabilidade.created = tempestadeProbabilidade.created ? moment(tempestadeProbabilidade.created) : undefined;
        tempestadeProbabilidade.updated = tempestadeProbabilidade.updated ? moment(tempestadeProbabilidade.updated) : undefined;
      });
    }
    return res;
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITempestadeNivel } from 'app/shared/model/tempestade-nivel.model';

type EntityResponseType = HttpResponse<ITempestadeNivel>;
type EntityArrayResponseType = HttpResponse<ITempestadeNivel[]>;

@Injectable({ providedIn: 'root' })
export class TempestadeNivelService {
  public resourceUrl = SERVER_API_URL + 'api/tempestade-nivels';

  constructor(protected http: HttpClient) {}

  create(tempestadeNivel: ITempestadeNivel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tempestadeNivel);
    return this.http
      .post<ITempestadeNivel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tempestadeNivel: ITempestadeNivel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tempestadeNivel);
    return this.http
      .put<ITempestadeNivel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITempestadeNivel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITempestadeNivel[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tempestadeNivel: ITempestadeNivel): ITempestadeNivel {
    const copy: ITempestadeNivel = Object.assign({}, tempestadeNivel, {
      created: tempestadeNivel.created && tempestadeNivel.created.isValid() ? tempestadeNivel.created.toJSON() : undefined,
      updated: tempestadeNivel.updated && tempestadeNivel.updated.isValid() ? tempestadeNivel.updated.toJSON() : undefined,
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
      res.body.forEach((tempestadeNivel: ITempestadeNivel) => {
        tempestadeNivel.created = tempestadeNivel.created ? moment(tempestadeNivel.created) : undefined;
        tempestadeNivel.updated = tempestadeNivel.updated ? moment(tempestadeNivel.updated) : undefined;
      });
    }
    return res;
  }
}

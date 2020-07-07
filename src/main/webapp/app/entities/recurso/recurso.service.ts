import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRecurso } from 'app/shared/model/recurso.model';

type EntityResponseType = HttpResponse<IRecurso>;
type EntityArrayResponseType = HttpResponse<IRecurso[]>;

@Injectable({ providedIn: 'root' })
export class RecursoService {
  public resourceUrl = SERVER_API_URL + 'api/recursos';

  constructor(protected http: HttpClient) {}

  create(recurso: IRecurso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recurso);
    return this.http
      .post<IRecurso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(recurso: IRecurso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recurso);
    return this.http
      .put<IRecurso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRecurso>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRecurso[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(recurso: IRecurso): IRecurso {
    const copy: IRecurso = Object.assign({}, recurso, {
      created: recurso.created && recurso.created.isValid() ? recurso.created.toJSON() : undefined,
      updated: recurso.updated && recurso.updated.isValid() ? recurso.updated.toJSON() : undefined,
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
      res.body.forEach((recurso: IRecurso) => {
        recurso.created = recurso.created ? moment(recurso.created) : undefined;
        recurso.updated = recurso.updated ? moment(recurso.updated) : undefined;
      });
    }
    return res;
  }
}

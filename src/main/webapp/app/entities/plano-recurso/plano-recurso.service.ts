import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlanoRecurso } from 'app/shared/model/plano-recurso.model';

type EntityResponseType = HttpResponse<IPlanoRecurso>;
type EntityArrayResponseType = HttpResponse<IPlanoRecurso[]>;

@Injectable({ providedIn: 'root' })
export class PlanoRecursoService {
  public resourceUrl = SERVER_API_URL + 'api/plano-recursos';

  constructor(protected http: HttpClient) {}

  create(planoRecurso: IPlanoRecurso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(planoRecurso);
    return this.http
      .post<IPlanoRecurso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(planoRecurso: IPlanoRecurso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(planoRecurso);
    return this.http
      .put<IPlanoRecurso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlanoRecurso>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlanoRecurso[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(planoRecurso: IPlanoRecurso): IPlanoRecurso {
    const copy: IPlanoRecurso = Object.assign({}, planoRecurso, {
      created: planoRecurso.created && planoRecurso.created.isValid() ? planoRecurso.created.toJSON() : undefined,
      updated: planoRecurso.updated && planoRecurso.updated.isValid() ? planoRecurso.updated.toJSON() : undefined,
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
      res.body.forEach((planoRecurso: IPlanoRecurso) => {
        planoRecurso.created = planoRecurso.created ? moment(planoRecurso.created) : undefined;
        planoRecurso.updated = planoRecurso.updated ? moment(planoRecurso.updated) : undefined;
      });
    }
    return res;
  }
}

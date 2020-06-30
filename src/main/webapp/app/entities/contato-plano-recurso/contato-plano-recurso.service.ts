import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContatoPlanoRecurso } from 'app/shared/model/contato-plano-recurso.model';

type EntityResponseType = HttpResponse<IContatoPlanoRecurso>;
type EntityArrayResponseType = HttpResponse<IContatoPlanoRecurso[]>;

@Injectable({ providedIn: 'root' })
export class ContatoPlanoRecursoService {
  public resourceUrl = SERVER_API_URL + 'api/contato-plano-recursos';

  constructor(protected http: HttpClient) {}

  create(contatoPlanoRecurso: IContatoPlanoRecurso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contatoPlanoRecurso);
    return this.http
      .post<IContatoPlanoRecurso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(contatoPlanoRecurso: IContatoPlanoRecurso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contatoPlanoRecurso);
    return this.http
      .put<IContatoPlanoRecurso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IContatoPlanoRecurso>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContatoPlanoRecurso[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(contatoPlanoRecurso: IContatoPlanoRecurso): IContatoPlanoRecurso {
    const copy: IContatoPlanoRecurso = Object.assign({}, contatoPlanoRecurso, {
      created: contatoPlanoRecurso.created && contatoPlanoRecurso.created.isValid() ? contatoPlanoRecurso.created.toJSON() : undefined,
      updated: contatoPlanoRecurso.updated && contatoPlanoRecurso.updated.isValid() ? contatoPlanoRecurso.updated.toJSON() : undefined,
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
      res.body.forEach((contatoPlanoRecurso: IContatoPlanoRecurso) => {
        contatoPlanoRecurso.created = contatoPlanoRecurso.created ? moment(contatoPlanoRecurso.created) : undefined;
        contatoPlanoRecurso.updated = contatoPlanoRecurso.updated ? moment(contatoPlanoRecurso.updated) : undefined;
      });
    }
    return res;
  }
}

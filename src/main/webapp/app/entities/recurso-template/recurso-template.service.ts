import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRecursoTemplate } from 'app/shared/model/recurso-template.model';

type EntityResponseType = HttpResponse<IRecursoTemplate>;
type EntityArrayResponseType = HttpResponse<IRecursoTemplate[]>;

@Injectable({ providedIn: 'root' })
export class RecursoTemplateService {
  public resourceUrl = SERVER_API_URL + 'api/recurso-templates';

  constructor(protected http: HttpClient) {}

  create(recursoTemplate: IRecursoTemplate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recursoTemplate);
    return this.http
      .post<IRecursoTemplate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(recursoTemplate: IRecursoTemplate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recursoTemplate);
    return this.http
      .put<IRecursoTemplate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRecursoTemplate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRecursoTemplate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(recursoTemplate: IRecursoTemplate): IRecursoTemplate {
    const copy: IRecursoTemplate = Object.assign({}, recursoTemplate, {
      created: recursoTemplate.created && recursoTemplate.created.isValid() ? recursoTemplate.created.toJSON() : undefined,
      updated: recursoTemplate.updated && recursoTemplate.updated.isValid() ? recursoTemplate.updated.toJSON() : undefined,
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
      res.body.forEach((recursoTemplate: IRecursoTemplate) => {
        recursoTemplate.created = recursoTemplate.created ? moment(recursoTemplate.created) : undefined;
        recursoTemplate.updated = recursoTemplate.updated ? moment(recursoTemplate.updated) : undefined;
      });
    }
    return res;
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFerramenta } from 'app/shared/model/ferramenta.model';

type EntityResponseType = HttpResponse<IFerramenta>;
type EntityArrayResponseType = HttpResponse<IFerramenta[]>;

@Injectable({ providedIn: 'root' })
export class FerramentaService {
  public resourceUrl = SERVER_API_URL + 'api/ferramentas';

  constructor(protected http: HttpClient) {}

  create(ferramenta: IFerramenta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ferramenta);
    return this.http
      .post<IFerramenta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ferramenta: IFerramenta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ferramenta);
    return this.http
      .put<IFerramenta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFerramenta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFerramenta[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(ferramenta: IFerramenta): IFerramenta {
    const copy: IFerramenta = Object.assign({}, ferramenta, {
      created: ferramenta.created && ferramenta.created.isValid() ? ferramenta.created.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.created = res.body.created ? moment(res.body.created) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((ferramenta: IFerramenta) => {
        ferramenta.created = ferramenta.created ? moment(ferramenta.created) : undefined;
      });
    }
    return res;
  }
}

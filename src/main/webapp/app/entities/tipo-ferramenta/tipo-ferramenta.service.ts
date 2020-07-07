import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoFerramenta } from 'app/shared/model/tipo-ferramenta.model';

type EntityResponseType = HttpResponse<ITipoFerramenta>;
type EntityArrayResponseType = HttpResponse<ITipoFerramenta[]>;

@Injectable({ providedIn: 'root' })
export class TipoFerramentaService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-ferramentas';

  constructor(protected http: HttpClient) {}

  create(tipoFerramenta: ITipoFerramenta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tipoFerramenta);
    return this.http
      .post<ITipoFerramenta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tipoFerramenta: ITipoFerramenta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tipoFerramenta);
    return this.http
      .put<ITipoFerramenta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITipoFerramenta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITipoFerramenta[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tipoFerramenta: ITipoFerramenta): ITipoFerramenta {
    const copy: ITipoFerramenta = Object.assign({}, tipoFerramenta, {
      created: tipoFerramenta.created && tipoFerramenta.created.isValid() ? tipoFerramenta.created.toJSON() : undefined,
      updated: tipoFerramenta.updated && tipoFerramenta.updated.isValid() ? tipoFerramenta.updated.toJSON() : undefined,
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
      res.body.forEach((tipoFerramenta: ITipoFerramenta) => {
        tipoFerramenta.created = tipoFerramenta.created ? moment(tipoFerramenta.created) : undefined;
        tipoFerramenta.updated = tipoFerramenta.updated ? moment(tipoFerramenta.updated) : undefined;
      });
    }
    return res;
  }
}

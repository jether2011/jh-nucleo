import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAcumuladoChuvaFaixa } from 'app/shared/model/acumulado-chuva-faixa.model';

type EntityResponseType = HttpResponse<IAcumuladoChuvaFaixa>;
type EntityArrayResponseType = HttpResponse<IAcumuladoChuvaFaixa[]>;

@Injectable({ providedIn: 'root' })
export class AcumuladoChuvaFaixaService {
  public resourceUrl = SERVER_API_URL + 'api/acumulado-chuva-faixas';

  constructor(protected http: HttpClient) {}

  create(acumuladoChuvaFaixa: IAcumuladoChuvaFaixa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(acumuladoChuvaFaixa);
    return this.http
      .post<IAcumuladoChuvaFaixa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(acumuladoChuvaFaixa: IAcumuladoChuvaFaixa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(acumuladoChuvaFaixa);
    return this.http
      .put<IAcumuladoChuvaFaixa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAcumuladoChuvaFaixa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAcumuladoChuvaFaixa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(acumuladoChuvaFaixa: IAcumuladoChuvaFaixa): IAcumuladoChuvaFaixa {
    const copy: IAcumuladoChuvaFaixa = Object.assign({}, acumuladoChuvaFaixa, {
      created: acumuladoChuvaFaixa.created && acumuladoChuvaFaixa.created.isValid() ? acumuladoChuvaFaixa.created.toJSON() : undefined,
      updated: acumuladoChuvaFaixa.updated && acumuladoChuvaFaixa.updated.isValid() ? acumuladoChuvaFaixa.updated.toJSON() : undefined,
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
      res.body.forEach((acumuladoChuvaFaixa: IAcumuladoChuvaFaixa) => {
        acumuladoChuvaFaixa.created = acumuladoChuvaFaixa.created ? moment(acumuladoChuvaFaixa.created) : undefined;
        acumuladoChuvaFaixa.updated = acumuladoChuvaFaixa.updated ? moment(acumuladoChuvaFaixa.updated) : undefined;
      });
    }
    return res;
  }
}

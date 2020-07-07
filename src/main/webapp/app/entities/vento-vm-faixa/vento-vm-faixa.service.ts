import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVentoVmFaixa } from 'app/shared/model/vento-vm-faixa.model';

type EntityResponseType = HttpResponse<IVentoVmFaixa>;
type EntityArrayResponseType = HttpResponse<IVentoVmFaixa[]>;

@Injectable({ providedIn: 'root' })
export class VentoVmFaixaService {
  public resourceUrl = SERVER_API_URL + 'api/vento-vm-faixas';

  constructor(protected http: HttpClient) {}

  create(ventoVmFaixa: IVentoVmFaixa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ventoVmFaixa);
    return this.http
      .post<IVentoVmFaixa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ventoVmFaixa: IVentoVmFaixa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ventoVmFaixa);
    return this.http
      .put<IVentoVmFaixa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVentoVmFaixa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVentoVmFaixa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(ventoVmFaixa: IVentoVmFaixa): IVentoVmFaixa {
    const copy: IVentoVmFaixa = Object.assign({}, ventoVmFaixa, {
      created: ventoVmFaixa.created && ventoVmFaixa.created.isValid() ? ventoVmFaixa.created.toJSON() : undefined,
      updated: ventoVmFaixa.updated && ventoVmFaixa.updated.isValid() ? ventoVmFaixa.updated.toJSON() : undefined,
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
      res.body.forEach((ventoVmFaixa: IVentoVmFaixa) => {
        ventoVmFaixa.created = ventoVmFaixa.created ? moment(ventoVmFaixa.created) : undefined;
        ventoVmFaixa.updated = ventoVmFaixa.updated ? moment(ventoVmFaixa.updated) : undefined;
      });
    }
    return res;
  }
}

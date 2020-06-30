import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlano } from 'app/shared/model/plano.model';

type EntityResponseType = HttpResponse<IPlano>;
type EntityArrayResponseType = HttpResponse<IPlano[]>;

@Injectable({ providedIn: 'root' })
export class PlanoService {
  public resourceUrl = SERVER_API_URL + 'api/planos';

  constructor(protected http: HttpClient) {}

  create(plano: IPlano): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plano);
    return this.http
      .post<IPlano>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(plano: IPlano): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plano);
    return this.http
      .put<IPlano>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlano>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlano[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(plano: IPlano): IPlano {
    const copy: IPlano = Object.assign({}, plano, {
      dtInicioContrato: plano.dtInicioContrato && plano.dtInicioContrato.isValid() ? plano.dtInicioContrato.format(DATE_FORMAT) : undefined,
      dataFimContrato: plano.dataFimContrato && plano.dataFimContrato.isValid() ? plano.dataFimContrato.format(DATE_FORMAT) : undefined,
      created: plano.created && plano.created.isValid() ? plano.created.toJSON() : undefined,
      updated: plano.updated && plano.updated.isValid() ? plano.updated.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dtInicioContrato = res.body.dtInicioContrato ? moment(res.body.dtInicioContrato) : undefined;
      res.body.dataFimContrato = res.body.dataFimContrato ? moment(res.body.dataFimContrato) : undefined;
      res.body.created = res.body.created ? moment(res.body.created) : undefined;
      res.body.updated = res.body.updated ? moment(res.body.updated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((plano: IPlano) => {
        plano.dtInicioContrato = plano.dtInicioContrato ? moment(plano.dtInicioContrato) : undefined;
        plano.dataFimContrato = plano.dataFimContrato ? moment(plano.dataFimContrato) : undefined;
        plano.created = plano.created ? moment(plano.created) : undefined;
        plano.updated = plano.updated ? moment(plano.updated) : undefined;
      });
    }
    return res;
  }
}

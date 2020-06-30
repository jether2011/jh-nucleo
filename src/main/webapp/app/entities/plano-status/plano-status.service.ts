import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlanoStatus } from 'app/shared/model/plano-status.model';

type EntityResponseType = HttpResponse<IPlanoStatus>;
type EntityArrayResponseType = HttpResponse<IPlanoStatus[]>;

@Injectable({ providedIn: 'root' })
export class PlanoStatusService {
  public resourceUrl = SERVER_API_URL + 'api/plano-statuses';

  constructor(protected http: HttpClient) {}

  create(planoStatus: IPlanoStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(planoStatus);
    return this.http
      .post<IPlanoStatus>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(planoStatus: IPlanoStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(planoStatus);
    return this.http
      .put<IPlanoStatus>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlanoStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlanoStatus[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(planoStatus: IPlanoStatus): IPlanoStatus {
    const copy: IPlanoStatus = Object.assign({}, planoStatus, {
      created: planoStatus.created && planoStatus.created.isValid() ? planoStatus.created.toJSON() : undefined,
      updated: planoStatus.updated && planoStatus.updated.isValid() ? planoStatus.updated.toJSON() : undefined,
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
      res.body.forEach((planoStatus: IPlanoStatus) => {
        planoStatus.created = planoStatus.created ? moment(planoStatus.created) : undefined;
        planoStatus.updated = planoStatus.updated ? moment(planoStatus.updated) : undefined;
      });
    }
    return res;
  }
}

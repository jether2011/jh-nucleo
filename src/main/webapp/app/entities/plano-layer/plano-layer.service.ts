import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlanoLayer } from 'app/shared/model/plano-layer.model';

type EntityResponseType = HttpResponse<IPlanoLayer>;
type EntityArrayResponseType = HttpResponse<IPlanoLayer[]>;

@Injectable({ providedIn: 'root' })
export class PlanoLayerService {
  public resourceUrl = SERVER_API_URL + 'api/plano-layers';

  constructor(protected http: HttpClient) {}

  create(planoLayer: IPlanoLayer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(planoLayer);
    return this.http
      .post<IPlanoLayer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(planoLayer: IPlanoLayer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(planoLayer);
    return this.http
      .put<IPlanoLayer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlanoLayer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlanoLayer[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(planoLayer: IPlanoLayer): IPlanoLayer {
    const copy: IPlanoLayer = Object.assign({}, planoLayer, {
      created: planoLayer.created && planoLayer.created.isValid() ? planoLayer.created.toJSON() : undefined,
      updated: planoLayer.updated && planoLayer.updated.isValid() ? planoLayer.updated.toJSON() : undefined,
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
      res.body.forEach((planoLayer: IPlanoLayer) => {
        planoLayer.created = planoLayer.created ? moment(planoLayer.created) : undefined;
        planoLayer.updated = planoLayer.updated ? moment(planoLayer.updated) : undefined;
      });
    }
    return res;
  }
}

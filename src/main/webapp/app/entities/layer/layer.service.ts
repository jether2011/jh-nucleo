import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILayer } from 'app/shared/model/layer.model';

type EntityResponseType = HttpResponse<ILayer>;
type EntityArrayResponseType = HttpResponse<ILayer[]>;

@Injectable({ providedIn: 'root' })
export class LayerService {
  public resourceUrl = SERVER_API_URL + 'api/layers';

  constructor(protected http: HttpClient) {}

  create(layer: ILayer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(layer);
    return this.http
      .post<ILayer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(layer: ILayer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(layer);
    return this.http
      .put<ILayer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILayer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILayer[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(layer: ILayer): ILayer {
    const copy: ILayer = Object.assign({}, layer, {
      created: layer.created && layer.created.isValid() ? layer.created.toJSON() : undefined,
      updated: layer.updated && layer.updated.isValid() ? layer.updated.toJSON() : undefined,
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
      res.body.forEach((layer: ILayer) => {
        layer.created = layer.created ? moment(layer.created) : undefined;
        layer.updated = layer.updated ? moment(layer.updated) : undefined;
      });
    }
    return res;
  }
}

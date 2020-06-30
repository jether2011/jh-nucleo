import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBoletim } from 'app/shared/model/boletim.model';

type EntityResponseType = HttpResponse<IBoletim>;
type EntityArrayResponseType = HttpResponse<IBoletim[]>;

@Injectable({ providedIn: 'root' })
export class BoletimService {
  public resourceUrl = SERVER_API_URL + 'api/boletims';

  constructor(protected http: HttpClient) {}

  create(boletim: IBoletim): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(boletim);
    return this.http
      .post<IBoletim>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(boletim: IBoletim): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(boletim);
    return this.http
      .put<IBoletim>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBoletim>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBoletim[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(boletim: IBoletim): IBoletim {
    const copy: IBoletim = Object.assign({}, boletim, {
      created: boletim.created && boletim.created.isValid() ? boletim.created.toJSON() : undefined,
      updated: boletim.updated && boletim.updated.isValid() ? boletim.updated.toJSON() : undefined,
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
      res.body.forEach((boletim: IBoletim) => {
        boletim.created = boletim.created ? moment(boletim.created) : undefined;
        boletim.updated = boletim.updated ? moment(boletim.updated) : undefined;
      });
    }
    return res;
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBoletimPrevObs } from 'app/shared/model/boletim-prev-obs.model';

type EntityResponseType = HttpResponse<IBoletimPrevObs>;
type EntityArrayResponseType = HttpResponse<IBoletimPrevObs[]>;

@Injectable({ providedIn: 'root' })
export class BoletimPrevObsService {
  public resourceUrl = SERVER_API_URL + 'api/boletim-prev-obs';

  constructor(protected http: HttpClient) {}

  create(boletimPrevObs: IBoletimPrevObs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(boletimPrevObs);
    return this.http
      .post<IBoletimPrevObs>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(boletimPrevObs: IBoletimPrevObs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(boletimPrevObs);
    return this.http
      .put<IBoletimPrevObs>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBoletimPrevObs>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBoletimPrevObs[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(boletimPrevObs: IBoletimPrevObs): IBoletimPrevObs {
    const copy: IBoletimPrevObs = Object.assign({}, boletimPrevObs, {
      created: boletimPrevObs.created && boletimPrevObs.created.isValid() ? boletimPrevObs.created.toJSON() : undefined,
      updated: boletimPrevObs.updated && boletimPrevObs.updated.isValid() ? boletimPrevObs.updated.toJSON() : undefined,
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
      res.body.forEach((boletimPrevObs: IBoletimPrevObs) => {
        boletimPrevObs.created = boletimPrevObs.created ? moment(boletimPrevObs.created) : undefined;
        boletimPrevObs.updated = boletimPrevObs.updated ? moment(boletimPrevObs.updated) : undefined;
      });
    }
    return res;
  }
}

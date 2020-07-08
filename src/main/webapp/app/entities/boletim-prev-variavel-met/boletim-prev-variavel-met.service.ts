import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBoletimPrevVariavelMet } from 'app/shared/model/boletim-prev-variavel-met.model';

type EntityResponseType = HttpResponse<IBoletimPrevVariavelMet>;
type EntityArrayResponseType = HttpResponse<IBoletimPrevVariavelMet[]>;

@Injectable({ providedIn: 'root' })
export class BoletimPrevVariavelMetService {
  public resourceUrl = SERVER_API_URL + 'api/boletim-prev-variavel-mets';

  constructor(protected http: HttpClient) {}

  create(boletimPrevVariavelMet: IBoletimPrevVariavelMet): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(boletimPrevVariavelMet);
    return this.http
      .post<IBoletimPrevVariavelMet>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(boletimPrevVariavelMet: IBoletimPrevVariavelMet): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(boletimPrevVariavelMet);
    return this.http
      .put<IBoletimPrevVariavelMet>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBoletimPrevVariavelMet>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBoletimPrevVariavelMet[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(boletimPrevVariavelMet: IBoletimPrevVariavelMet): IBoletimPrevVariavelMet {
    const copy: IBoletimPrevVariavelMet = Object.assign({}, boletimPrevVariavelMet, {
      created:
        boletimPrevVariavelMet.created && boletimPrevVariavelMet.created.isValid() ? boletimPrevVariavelMet.created.toJSON() : undefined,
      updated:
        boletimPrevVariavelMet.updated && boletimPrevVariavelMet.updated.isValid() ? boletimPrevVariavelMet.updated.toJSON() : undefined,
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
      res.body.forEach((boletimPrevVariavelMet: IBoletimPrevVariavelMet) => {
        boletimPrevVariavelMet.created = boletimPrevVariavelMet.created ? moment(boletimPrevVariavelMet.created) : undefined;
        boletimPrevVariavelMet.updated = boletimPrevVariavelMet.updated ? moment(boletimPrevVariavelMet.updated) : undefined;
      });
    }
    return res;
  }
}

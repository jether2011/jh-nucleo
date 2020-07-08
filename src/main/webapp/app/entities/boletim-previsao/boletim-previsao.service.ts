import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBoletimPrevisao } from 'app/shared/model/boletim-previsao.model';

type EntityResponseType = HttpResponse<IBoletimPrevisao>;
type EntityArrayResponseType = HttpResponse<IBoletimPrevisao[]>;

@Injectable({ providedIn: 'root' })
export class BoletimPrevisaoService {
  public resourceUrl = SERVER_API_URL + 'api/boletim-previsaos';

  constructor(protected http: HttpClient) {}

  create(boletimPrevisao: IBoletimPrevisao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(boletimPrevisao);
    return this.http
      .post<IBoletimPrevisao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(boletimPrevisao: IBoletimPrevisao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(boletimPrevisao);
    return this.http
      .put<IBoletimPrevisao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBoletimPrevisao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBoletimPrevisao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(boletimPrevisao: IBoletimPrevisao): IBoletimPrevisao {
    const copy: IBoletimPrevisao = Object.assign({}, boletimPrevisao, {
      created: boletimPrevisao.created && boletimPrevisao.created.isValid() ? boletimPrevisao.created.toJSON() : undefined,
      updated: boletimPrevisao.updated && boletimPrevisao.updated.isValid() ? boletimPrevisao.updated.toJSON() : undefined,
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
      res.body.forEach((boletimPrevisao: IBoletimPrevisao) => {
        boletimPrevisao.created = boletimPrevisao.created ? moment(boletimPrevisao.created) : undefined;
        boletimPrevisao.updated = boletimPrevisao.updated ? moment(boletimPrevisao.updated) : undefined;
      });
    }
    return res;
  }
}

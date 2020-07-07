import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUmidadeAr } from 'app/shared/model/umidade-ar.model';

type EntityResponseType = HttpResponse<IUmidadeAr>;
type EntityArrayResponseType = HttpResponse<IUmidadeAr[]>;

@Injectable({ providedIn: 'root' })
export class UmidadeArService {
  public resourceUrl = SERVER_API_URL + 'api/umidade-ars';

  constructor(protected http: HttpClient) {}

  create(umidadeAr: IUmidadeAr): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(umidadeAr);
    return this.http
      .post<IUmidadeAr>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(umidadeAr: IUmidadeAr): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(umidadeAr);
    return this.http
      .put<IUmidadeAr>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUmidadeAr>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUmidadeAr[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(umidadeAr: IUmidadeAr): IUmidadeAr {
    const copy: IUmidadeAr = Object.assign({}, umidadeAr, {
      created: umidadeAr.created && umidadeAr.created.isValid() ? umidadeAr.created.toJSON() : undefined,
      updated: umidadeAr.updated && umidadeAr.updated.isValid() ? umidadeAr.updated.toJSON() : undefined,
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
      res.body.forEach((umidadeAr: IUmidadeAr) => {
        umidadeAr.created = umidadeAr.created ? moment(umidadeAr.created) : undefined;
        umidadeAr.updated = umidadeAr.updated ? moment(umidadeAr.updated) : undefined;
      });
    }
    return res;
  }
}

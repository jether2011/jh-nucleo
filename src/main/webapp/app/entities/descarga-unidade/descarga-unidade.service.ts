import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDescargaUnidade } from 'app/shared/model/descarga-unidade.model';

type EntityResponseType = HttpResponse<IDescargaUnidade>;
type EntityArrayResponseType = HttpResponse<IDescargaUnidade[]>;

@Injectable({ providedIn: 'root' })
export class DescargaUnidadeService {
  public resourceUrl = SERVER_API_URL + 'api/descarga-unidades';

  constructor(protected http: HttpClient) {}

  create(descargaUnidade: IDescargaUnidade): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(descargaUnidade);
    return this.http
      .post<IDescargaUnidade>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(descargaUnidade: IDescargaUnidade): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(descargaUnidade);
    return this.http
      .put<IDescargaUnidade>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDescargaUnidade>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDescargaUnidade[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(descargaUnidade: IDescargaUnidade): IDescargaUnidade {
    const copy: IDescargaUnidade = Object.assign({}, descargaUnidade, {
      created: descargaUnidade.created && descargaUnidade.created.isValid() ? descargaUnidade.created.toJSON() : undefined,
      updated: descargaUnidade.updated && descargaUnidade.updated.isValid() ? descargaUnidade.updated.toJSON() : undefined,
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
      res.body.forEach((descargaUnidade: IDescargaUnidade) => {
        descargaUnidade.created = descargaUnidade.created ? moment(descargaUnidade.created) : undefined;
        descargaUnidade.updated = descargaUnidade.updated ? moment(descargaUnidade.updated) : undefined;
      });
    }
    return res;
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVariavelMeteorologica } from 'app/shared/model/variavel-meteorologica.model';

type EntityResponseType = HttpResponse<IVariavelMeteorologica>;
type EntityArrayResponseType = HttpResponse<IVariavelMeteorologica[]>;

@Injectable({ providedIn: 'root' })
export class VariavelMeteorologicaService {
  public resourceUrl = SERVER_API_URL + 'api/variavel-meteorologicas';

  constructor(protected http: HttpClient) {}

  create(variavelMeteorologica: IVariavelMeteorologica): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(variavelMeteorologica);
    return this.http
      .post<IVariavelMeteorologica>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(variavelMeteorologica: IVariavelMeteorologica): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(variavelMeteorologica);
    return this.http
      .put<IVariavelMeteorologica>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVariavelMeteorologica>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVariavelMeteorologica[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(variavelMeteorologica: IVariavelMeteorologica): IVariavelMeteorologica {
    const copy: IVariavelMeteorologica = Object.assign({}, variavelMeteorologica, {
      created:
        variavelMeteorologica.created && variavelMeteorologica.created.isValid() ? variavelMeteorologica.created.toJSON() : undefined,
      updated:
        variavelMeteorologica.updated && variavelMeteorologica.updated.isValid() ? variavelMeteorologica.updated.toJSON() : undefined,
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
      res.body.forEach((variavelMeteorologica: IVariavelMeteorologica) => {
        variavelMeteorologica.created = variavelMeteorologica.created ? moment(variavelMeteorologica.created) : undefined;
        variavelMeteorologica.updated = variavelMeteorologica.updated ? moment(variavelMeteorologica.updated) : undefined;
      });
    }
    return res;
  }
}

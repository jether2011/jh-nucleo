import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContatoTipoEnvio } from 'app/shared/model/contato-tipo-envio.model';

type EntityResponseType = HttpResponse<IContatoTipoEnvio>;
type EntityArrayResponseType = HttpResponse<IContatoTipoEnvio[]>;

@Injectable({ providedIn: 'root' })
export class ContatoTipoEnvioService {
  public resourceUrl = SERVER_API_URL + 'api/contato-tipo-envios';

  constructor(protected http: HttpClient) {}

  create(contatoTipoEnvio: IContatoTipoEnvio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contatoTipoEnvio);
    return this.http
      .post<IContatoTipoEnvio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(contatoTipoEnvio: IContatoTipoEnvio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contatoTipoEnvio);
    return this.http
      .put<IContatoTipoEnvio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IContatoTipoEnvio>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContatoTipoEnvio[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(contatoTipoEnvio: IContatoTipoEnvio): IContatoTipoEnvio {
    const copy: IContatoTipoEnvio = Object.assign({}, contatoTipoEnvio, {
      created: contatoTipoEnvio.created && contatoTipoEnvio.created.isValid() ? contatoTipoEnvio.created.toJSON() : undefined,
      updated: contatoTipoEnvio.updated && contatoTipoEnvio.updated.isValid() ? contatoTipoEnvio.updated.toJSON() : undefined,
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
      res.body.forEach((contatoTipoEnvio: IContatoTipoEnvio) => {
        contatoTipoEnvio.created = contatoTipoEnvio.created ? moment(contatoTipoEnvio.created) : undefined;
        contatoTipoEnvio.updated = contatoTipoEnvio.updated ? moment(contatoTipoEnvio.updated) : undefined;
      });
    }
    return res;
  }
}

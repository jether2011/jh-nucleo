import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INotificacaoEnviada } from 'app/shared/model/notificacao-enviada.model';

type EntityResponseType = HttpResponse<INotificacaoEnviada>;
type EntityArrayResponseType = HttpResponse<INotificacaoEnviada[]>;

@Injectable({ providedIn: 'root' })
export class NotificacaoEnviadaService {
  public resourceUrl = SERVER_API_URL + 'api/notificacao-enviadas';

  constructor(protected http: HttpClient) {}

  create(notificacaoEnviada: INotificacaoEnviada): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notificacaoEnviada);
    return this.http
      .post<INotificacaoEnviada>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(notificacaoEnviada: INotificacaoEnviada): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notificacaoEnviada);
    return this.http
      .put<INotificacaoEnviada>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INotificacaoEnviada>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INotificacaoEnviada[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(notificacaoEnviada: INotificacaoEnviada): INotificacaoEnviada {
    const copy: INotificacaoEnviada = Object.assign({}, notificacaoEnviada, {
      amazonDateLog:
        notificacaoEnviada.amazonDateLog && notificacaoEnviada.amazonDateLog.isValid()
          ? notificacaoEnviada.amazonDateLog.toJSON()
          : undefined,
      created: notificacaoEnviada.created && notificacaoEnviada.created.isValid() ? notificacaoEnviada.created.toJSON() : undefined,
      updated: notificacaoEnviada.updated && notificacaoEnviada.updated.isValid() ? notificacaoEnviada.updated.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.amazonDateLog = res.body.amazonDateLog ? moment(res.body.amazonDateLog) : undefined;
      res.body.created = res.body.created ? moment(res.body.created) : undefined;
      res.body.updated = res.body.updated ? moment(res.body.updated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((notificacaoEnviada: INotificacaoEnviada) => {
        notificacaoEnviada.amazonDateLog = notificacaoEnviada.amazonDateLog ? moment(notificacaoEnviada.amazonDateLog) : undefined;
        notificacaoEnviada.created = notificacaoEnviada.created ? moment(notificacaoEnviada.created) : undefined;
        notificacaoEnviada.updated = notificacaoEnviada.updated ? moment(notificacaoEnviada.updated) : undefined;
      });
    }
    return res;
  }
}

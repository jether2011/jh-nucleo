import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAlvo } from 'app/shared/model/alvo.model';

type EntityResponseType = HttpResponse<IAlvo>;
type EntityArrayResponseType = HttpResponse<IAlvo[]>;

@Injectable({ providedIn: 'root' })
export class AlvoService {
  public resourceUrl = SERVER_API_URL + 'api/alvos';

  constructor(protected http: HttpClient) {}

  create(alvo: IAlvo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alvo);
    return this.http
      .post<IAlvo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(alvo: IAlvo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alvo);
    return this.http
      .put<IAlvo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAlvo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAlvo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(alvo: IAlvo): IAlvo {
    const copy: IAlvo = Object.assign({}, alvo, {
      horarioLiberacao: alvo.horarioLiberacao && alvo.horarioLiberacao.isValid() ? alvo.horarioLiberacao.toJSON() : undefined,
      horario: alvo.horario && alvo.horario.isValid() ? alvo.horario.toJSON() : undefined,
      dataDesativado: alvo.dataDesativado && alvo.dataDesativado.isValid() ? alvo.dataDesativado.toJSON() : undefined,
      horarioBloqueioNotificacao:
        alvo.horarioBloqueioNotificacao && alvo.horarioBloqueioNotificacao.isValid() ? alvo.horarioBloqueioNotificacao.toJSON() : undefined,
      created: alvo.created && alvo.created.isValid() ? alvo.created.toJSON() : undefined,
      updated: alvo.updated && alvo.updated.isValid() ? alvo.updated.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.horarioLiberacao = res.body.horarioLiberacao ? moment(res.body.horarioLiberacao) : undefined;
      res.body.horario = res.body.horario ? moment(res.body.horario) : undefined;
      res.body.dataDesativado = res.body.dataDesativado ? moment(res.body.dataDesativado) : undefined;
      res.body.horarioBloqueioNotificacao = res.body.horarioBloqueioNotificacao ? moment(res.body.horarioBloqueioNotificacao) : undefined;
      res.body.created = res.body.created ? moment(res.body.created) : undefined;
      res.body.updated = res.body.updated ? moment(res.body.updated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((alvo: IAlvo) => {
        alvo.horarioLiberacao = alvo.horarioLiberacao ? moment(alvo.horarioLiberacao) : undefined;
        alvo.horario = alvo.horario ? moment(alvo.horario) : undefined;
        alvo.dataDesativado = alvo.dataDesativado ? moment(alvo.dataDesativado) : undefined;
        alvo.horarioBloqueioNotificacao = alvo.horarioBloqueioNotificacao ? moment(alvo.horarioBloqueioNotificacao) : undefined;
        alvo.created = alvo.created ? moment(alvo.created) : undefined;
        alvo.updated = alvo.updated ? moment(alvo.updated) : undefined;
      });
    }
    return res;
  }
}

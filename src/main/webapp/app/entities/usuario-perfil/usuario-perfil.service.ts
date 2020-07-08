import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUsuarioPerfil } from 'app/shared/model/usuario-perfil.model';

type EntityResponseType = HttpResponse<IUsuarioPerfil>;
type EntityArrayResponseType = HttpResponse<IUsuarioPerfil[]>;

@Injectable({ providedIn: 'root' })
export class UsuarioPerfilService {
  public resourceUrl = SERVER_API_URL + 'api/usuario-perfils';

  constructor(protected http: HttpClient) {}

  create(usuarioPerfil: IUsuarioPerfil): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuarioPerfil);
    return this.http
      .post<IUsuarioPerfil>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(usuarioPerfil: IUsuarioPerfil): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuarioPerfil);
    return this.http
      .put<IUsuarioPerfil>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUsuarioPerfil>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUsuarioPerfil[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(usuarioPerfil: IUsuarioPerfil): IUsuarioPerfil {
    const copy: IUsuarioPerfil = Object.assign({}, usuarioPerfil, {
      created: usuarioPerfil.created && usuarioPerfil.created.isValid() ? usuarioPerfil.created.toJSON() : undefined,
      updated: usuarioPerfil.updated && usuarioPerfil.updated.isValid() ? usuarioPerfil.updated.toJSON() : undefined,
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
      res.body.forEach((usuarioPerfil: IUsuarioPerfil) => {
        usuarioPerfil.created = usuarioPerfil.created ? moment(usuarioPerfil.created) : undefined;
        usuarioPerfil.updated = usuarioPerfil.updated ? moment(usuarioPerfil.updated) : undefined;
      });
    }
    return res;
  }
}

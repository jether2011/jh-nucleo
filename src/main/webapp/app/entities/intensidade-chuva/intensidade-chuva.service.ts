import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIntensidadeChuva } from 'app/shared/model/intensidade-chuva.model';

type EntityResponseType = HttpResponse<IIntensidadeChuva>;
type EntityArrayResponseType = HttpResponse<IIntensidadeChuva[]>;

@Injectable({ providedIn: 'root' })
export class IntensidadeChuvaService {
  public resourceUrl = SERVER_API_URL + 'api/intensidade-chuvas';

  constructor(protected http: HttpClient) {}

  create(intensidadeChuva: IIntensidadeChuva): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(intensidadeChuva);
    return this.http
      .post<IIntensidadeChuva>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(intensidadeChuva: IIntensidadeChuva): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(intensidadeChuva);
    return this.http
      .put<IIntensidadeChuva>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIntensidadeChuva>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIntensidadeChuva[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(intensidadeChuva: IIntensidadeChuva): IIntensidadeChuva {
    const copy: IIntensidadeChuva = Object.assign({}, intensidadeChuva, {
      created: intensidadeChuva.created && intensidadeChuva.created.isValid() ? intensidadeChuva.created.toJSON() : undefined,
      updated: intensidadeChuva.updated && intensidadeChuva.updated.isValid() ? intensidadeChuva.updated.toJSON() : undefined,
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
      res.body.forEach((intensidadeChuva: IIntensidadeChuva) => {
        intensidadeChuva.created = intensidadeChuva.created ? moment(intensidadeChuva.created) : undefined;
        intensidadeChuva.updated = intensidadeChuva.updated ? moment(intensidadeChuva.updated) : undefined;
      });
    }
    return res;
  }
}

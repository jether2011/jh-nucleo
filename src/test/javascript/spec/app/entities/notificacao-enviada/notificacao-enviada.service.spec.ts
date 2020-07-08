import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { NotificacaoEnviadaService } from 'app/entities/notificacao-enviada/notificacao-enviada.service';
import { INotificacaoEnviada, NotificacaoEnviada } from 'app/shared/model/notificacao-enviada.model';

describe('Service Tests', () => {
  describe('NotificacaoEnviada Service', () => {
    let injector: TestBed;
    let service: NotificacaoEnviadaService;
    let httpMock: HttpTestingController;
    let elemDefault: INotificacaoEnviada;
    let expectedResult: INotificacaoEnviada | INotificacaoEnviada[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(NotificacaoEnviadaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new NotificacaoEnviada(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        currentDate,
        0,
        'AAAAAAA',
        0,
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            amazonDateLog: currentDate.format(DATE_TIME_FORMAT),
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a NotificacaoEnviada', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            amazonDateLog: currentDate.format(DATE_TIME_FORMAT),
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            amazonDateLog: currentDate,
            created: currentDate,
            updated: currentDate,
          },
          returnedFromService
        );

        service.create(new NotificacaoEnviada()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a NotificacaoEnviada', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            descricao: 'BBBBBB',
            destinatarios: 'BBBBBB',
            tipo: 'BBBBBB',
            status: 'BBBBBB',
            assunto: 'BBBBBB',
            enviado: 1,
            contador: 1,
            amazonMessageId: 'BBBBBB',
            amazonDateLog: currentDate.format(DATE_TIME_FORMAT),
            priceInUsd: 1,
            amazonResposta: 'BBBBBB',
            referenceId: 1,
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            amazonDateLog: currentDate,
            created: currentDate,
            updated: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of NotificacaoEnviada', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            descricao: 'BBBBBB',
            destinatarios: 'BBBBBB',
            tipo: 'BBBBBB',
            status: 'BBBBBB',
            assunto: 'BBBBBB',
            enviado: 1,
            contador: 1,
            amazonMessageId: 'BBBBBB',
            amazonDateLog: currentDate.format(DATE_TIME_FORMAT),
            priceInUsd: 1,
            amazonResposta: 'BBBBBB',
            referenceId: 1,
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            amazonDateLog: currentDate,
            created: currentDate,
            updated: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a NotificacaoEnviada', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

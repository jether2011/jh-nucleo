import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AlvoService } from 'app/entities/alvo/alvo.service';
import { IAlvo, Alvo } from 'app/shared/model/alvo.model';

describe('Service Tests', () => {
  describe('Alvo Service', () => {
    let injector: TestBed;
    let service: AlvoService;
    let httpMock: HttpTestingController;
    let elemDefault: IAlvo;
    let expectedResult: IAlvo | IAlvo[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AlvoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Alvo(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        false,
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            horarioLiberacao: currentDate.format(DATE_TIME_FORMAT),
            horario: currentDate.format(DATE_TIME_FORMAT),
            dataDesativado: currentDate.format(DATE_TIME_FORMAT),
            horarioBloqueioNotificacao: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a Alvo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            horarioLiberacao: currentDate.format(DATE_TIME_FORMAT),
            horario: currentDate.format(DATE_TIME_FORMAT),
            dataDesativado: currentDate.format(DATE_TIME_FORMAT),
            horarioBloqueioNotificacao: currentDate.format(DATE_TIME_FORMAT),
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            horarioLiberacao: currentDate,
            horario: currentDate,
            dataDesativado: currentDate,
            horarioBloqueioNotificacao: currentDate,
            created: currentDate,
            updated: currentDate,
          },
          returnedFromService
        );

        service.create(new Alvo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Alvo', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            nomeReduzido: 'BBBBBB',
            descricao: 'BBBBBB',
            primeiroPonto: 'BBBBBB',
            ultimoPonto: 'BBBBBB',
            horarioLiberacao: currentDate.format(DATE_TIME_FORMAT),
            horario: currentDate.format(DATE_TIME_FORMAT),
            duracao: 'BBBBBB',
            duracaoAtual: 'BBBBBB',
            dataDesativado: currentDate.format(DATE_TIME_FORMAT),
            coordenadasAlertaPontos: 'BBBBBB',
            coordenadasLiberacaoPontos: 'BBBBBB',
            telegramTokenBot: 'BBBBBB',
            telegramChatId: 'BBBBBB',
            horarioBloqueioNotificacao: currentDate.format(DATE_TIME_FORMAT),
            coordenadasOriginalPontos: 'BBBBBB',
            ativo: true,
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            horarioLiberacao: currentDate,
            horario: currentDate,
            dataDesativado: currentDate,
            horarioBloqueioNotificacao: currentDate,
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

      it('should return a list of Alvo', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            nomeReduzido: 'BBBBBB',
            descricao: 'BBBBBB',
            primeiroPonto: 'BBBBBB',
            ultimoPonto: 'BBBBBB',
            horarioLiberacao: currentDate.format(DATE_TIME_FORMAT),
            horario: currentDate.format(DATE_TIME_FORMAT),
            duracao: 'BBBBBB',
            duracaoAtual: 'BBBBBB',
            dataDesativado: currentDate.format(DATE_TIME_FORMAT),
            coordenadasAlertaPontos: 'BBBBBB',
            coordenadasLiberacaoPontos: 'BBBBBB',
            telegramTokenBot: 'BBBBBB',
            telegramChatId: 'BBBBBB',
            horarioBloqueioNotificacao: currentDate.format(DATE_TIME_FORMAT),
            coordenadasOriginalPontos: 'BBBBBB',
            ativo: true,
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            horarioLiberacao: currentDate,
            horario: currentDate,
            dataDesativado: currentDate,
            horarioBloqueioNotificacao: currentDate,
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

      it('should delete a Alvo', () => {
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

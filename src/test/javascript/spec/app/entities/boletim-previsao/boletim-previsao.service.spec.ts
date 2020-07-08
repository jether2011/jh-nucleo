import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { BoletimPrevisaoService } from 'app/entities/boletim-previsao/boletim-previsao.service';
import { IBoletimPrevisao, BoletimPrevisao } from 'app/shared/model/boletim-previsao.model';

describe('Service Tests', () => {
  describe('BoletimPrevisao Service', () => {
    let injector: TestBed;
    let service: BoletimPrevisaoService;
    let httpMock: HttpTestingController;
    let elemDefault: IBoletimPrevisao;
    let expectedResult: IBoletimPrevisao | IBoletimPrevisao[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BoletimPrevisaoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new BoletimPrevisao(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        0,
        'AAAAAAA',
        0,
        0,
        0,
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
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

      it('should create a BoletimPrevisao', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            created: currentDate,
            updated: currentDate,
          },
          returnedFromService
        );

        service.create(new BoletimPrevisao()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BoletimPrevisao', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            descricao: 'BBBBBB',
            local: 'BBBBBB',
            imgCondicaoTempo: 1,
            observacao: 'BBBBBB',
            grupoOrdem: 1,
            ondas: 'BBBBBB',
            temperaturaDe: 1,
            temperaturaAte: 1,
            ventovelocidademediakmh: 1,
            ventosObservacao: 'BBBBBB',
            ventoRajada: true,
            tempestadeObservacao: 'BBBBBB',
            chuvaObservacao: 'BBBBBB',
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should return a list of BoletimPrevisao', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            descricao: 'BBBBBB',
            local: 'BBBBBB',
            imgCondicaoTempo: 1,
            observacao: 'BBBBBB',
            grupoOrdem: 1,
            ondas: 'BBBBBB',
            temperaturaDe: 1,
            temperaturaAte: 1,
            ventovelocidademediakmh: 1,
            ventosObservacao: 'BBBBBB',
            ventoRajada: true,
            tempestadeObservacao: 'BBBBBB',
            chuvaObservacao: 'BBBBBB',
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should delete a BoletimPrevisao', () => {
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

import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { DescargaService } from 'app/entities/descarga/descarga.service';
import { IDescarga, Descarga } from 'app/shared/model/descarga.model';

describe('Service Tests', () => {
  describe('Descarga Service', () => {
    let injector: TestBed;
    let service: DescargaService;
    let httpMock: HttpTestingController;
    let elemDefault: IDescarga;
    let expectedResult: IDescarga | IDescarga[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DescargaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Descarga(0, 'AAAAAAA', 'AAAAAAA', 0, currentDate, 'AAAAAAA', currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataPrimeiraDescarga: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a Descarga', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataPrimeiraDescarga: currentDate.format(DATE_TIME_FORMAT),
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataPrimeiraDescarga: currentDate,
            created: currentDate,
            updated: currentDate,
          },
          returnedFromService
        );

        service.create(new Descarga()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Descarga', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            descricao: 'BBBBBB',
            qtd: 1,
            dataPrimeiraDescarga: currentDate.format(DATE_TIME_FORMAT),
            tempoAntecipacao: 'BBBBBB',
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataPrimeiraDescarga: currentDate,
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

      it('should return a list of Descarga', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            descricao: 'BBBBBB',
            qtd: 1,
            dataPrimeiraDescarga: currentDate.format(DATE_TIME_FORMAT),
            tempoAntecipacao: 'BBBBBB',
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataPrimeiraDescarga: currentDate,
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

      it('should delete a Descarga', () => {
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

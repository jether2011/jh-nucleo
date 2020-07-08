import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AvisoMeteorologicoService } from 'app/entities/aviso-meteorologico/aviso-meteorologico.service';
import { IAvisoMeteorologico, AvisoMeteorologico } from 'app/shared/model/aviso-meteorologico.model';

describe('Service Tests', () => {
  describe('AvisoMeteorologico Service', () => {
    let injector: TestBed;
    let service: AvisoMeteorologicoService;
    let httpMock: HttpTestingController;
    let elemDefault: IAvisoMeteorologico;
    let expectedResult: IAvisoMeteorologico | IAvisoMeteorologico[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AvisoMeteorologicoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AvisoMeteorologico(
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
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
            inicio: currentDate.format(DATE_TIME_FORMAT),
            fim: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a AvisoMeteorologico', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            inicio: currentDate.format(DATE_TIME_FORMAT),
            fim: currentDate.format(DATE_TIME_FORMAT),
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inicio: currentDate,
            fim: currentDate,
            created: currentDate,
            updated: currentDate,
          },
          returnedFromService
        );

        service.create(new AvisoMeteorologico()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AvisoMeteorologico', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            assunto: 'BBBBBB',
            inicio: currentDate.format(DATE_TIME_FORMAT),
            fim: currentDate.format(DATE_TIME_FORMAT),
            texto: 'BBBBBB',
            imagem: 'BBBBBB',
            imagemAssinatura: 'BBBBBB',
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inicio: currentDate,
            fim: currentDate,
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

      it('should return a list of AvisoMeteorologico', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            assunto: 'BBBBBB',
            inicio: currentDate.format(DATE_TIME_FORMAT),
            fim: currentDate.format(DATE_TIME_FORMAT),
            texto: 'BBBBBB',
            imagem: 'BBBBBB',
            imagemAssinatura: 'BBBBBB',
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inicio: currentDate,
            fim: currentDate,
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

      it('should delete a AvisoMeteorologico', () => {
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

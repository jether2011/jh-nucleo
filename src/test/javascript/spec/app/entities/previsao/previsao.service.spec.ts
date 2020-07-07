import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PrevisaoService } from 'app/entities/previsao/previsao.service';
import { IPrevisao, Previsao } from 'app/shared/model/previsao.model';

describe('Service Tests', () => {
  describe('Previsao Service', () => {
    let injector: TestBed;
    let service: PrevisaoService;
    let httpMock: HttpTestingController;
    let elemDefault: IPrevisao;
    let expectedResult: IPrevisao | IPrevisao[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PrevisaoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Previsao(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
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

      it('should create a Previsao', () => {
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

        service.create(new Previsao()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Previsao', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            descricao: 'BBBBBB',
            textoNorte: 'BBBBBB',
            textoNorteImagem: 'BBBBBB',
            textoNordeste: 'BBBBBB',
            textoNordesteImagem: 'BBBBBB',
            textoSul: 'BBBBBB',
            textoSulImagem: 'BBBBBB',
            textoSudeste: 'BBBBBB',
            textoSudesteImagem: 'BBBBBB',
            textoCentroOeste: 'BBBBBB',
            textoCentroOesteImagem: 'BBBBBB',
            enviado: true,
            textoBrasil: 'BBBBBB',
            textoBrasilImagem: 'BBBBBB',
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

      it('should return a list of Previsao', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            descricao: 'BBBBBB',
            textoNorte: 'BBBBBB',
            textoNorteImagem: 'BBBBBB',
            textoNordeste: 'BBBBBB',
            textoNordesteImagem: 'BBBBBB',
            textoSul: 'BBBBBB',
            textoSulImagem: 'BBBBBB',
            textoSudeste: 'BBBBBB',
            textoSudesteImagem: 'BBBBBB',
            textoCentroOeste: 'BBBBBB',
            textoCentroOesteImagem: 'BBBBBB',
            enviado: true,
            textoBrasil: 'BBBBBB',
            textoBrasilImagem: 'BBBBBB',
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

      it('should delete a Previsao', () => {
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

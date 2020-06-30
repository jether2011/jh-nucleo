import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PlanoService } from 'app/entities/plano/plano.service';
import { IPlano, Plano } from 'app/shared/model/plano.model';

describe('Service Tests', () => {
  describe('Plano Service', () => {
    let injector: TestBed;
    let service: PlanoService;
    let httpMock: HttpTestingController;
    let elemDefault: IPlano;
    let expectedResult: IPlano | IPlano[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PlanoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Plano(
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        0,
        'AAAAAAA',
        0,
        currentDate,
        currentDate,
        'AAAAAAA',
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
            dtInicioContrato: currentDate.format(DATE_FORMAT),
            dataFimContrato: currentDate.format(DATE_FORMAT),
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

      it('should create a Plano', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dtInicioContrato: currentDate.format(DATE_FORMAT),
            dataFimContrato: currentDate.format(DATE_FORMAT),
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dtInicioContrato: currentDate,
            dataFimContrato: currentDate,
            created: currentDate,
            updated: currentDate,
          },
          returnedFromService
        );

        service.create(new Plano()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Plano', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            descricao: 'BBBBBB',
            horarioPrevisto: 1,
            trackingAtivo: 1,
            plrAtivo: 1,
            codigoWidgetPrevisao: 1,
            kmlAlvo: 'BBBBBB',
            zoomMin: 1,
            dtInicioContrato: currentDate.format(DATE_FORMAT),
            dataFimContrato: currentDate.format(DATE_FORMAT),
            horarioMonitInicio: 'BBBBBB',
            horarioMonitFinal: 'BBBBBB',
            blocos: 'BBBBBB',
            extent: 'BBBBBB',
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dtInicioContrato: currentDate,
            dataFimContrato: currentDate,
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

      it('should return a list of Plano', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            descricao: 'BBBBBB',
            horarioPrevisto: 1,
            trackingAtivo: 1,
            plrAtivo: 1,
            codigoWidgetPrevisao: 1,
            kmlAlvo: 'BBBBBB',
            zoomMin: 1,
            dtInicioContrato: currentDate.format(DATE_FORMAT),
            dataFimContrato: currentDate.format(DATE_FORMAT),
            horarioMonitInicio: 'BBBBBB',
            horarioMonitFinal: 'BBBBBB',
            blocos: 'BBBBBB',
            extent: 'BBBBBB',
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dtInicioContrato: currentDate,
            dataFimContrato: currentDate,
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

      it('should delete a Plano', () => {
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

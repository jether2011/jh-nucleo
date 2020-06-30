import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { NucleoTestModule } from '../../../test.module';
import { AcumuladoChuvaFaixaComponent } from 'app/entities/acumulado-chuva-faixa/acumulado-chuva-faixa.component';
import { AcumuladoChuvaFaixaService } from 'app/entities/acumulado-chuva-faixa/acumulado-chuva-faixa.service';
import { AcumuladoChuvaFaixa } from 'app/shared/model/acumulado-chuva-faixa.model';

describe('Component Tests', () => {
  describe('AcumuladoChuvaFaixa Management Component', () => {
    let comp: AcumuladoChuvaFaixaComponent;
    let fixture: ComponentFixture<AcumuladoChuvaFaixaComponent>;
    let service: AcumuladoChuvaFaixaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AcumuladoChuvaFaixaComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(AcumuladoChuvaFaixaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AcumuladoChuvaFaixaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AcumuladoChuvaFaixaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AcumuladoChuvaFaixa(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.acumuladoChuvaFaixas && comp.acumuladoChuvaFaixas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AcumuladoChuvaFaixa(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.acumuladoChuvaFaixas && comp.acumuladoChuvaFaixas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});

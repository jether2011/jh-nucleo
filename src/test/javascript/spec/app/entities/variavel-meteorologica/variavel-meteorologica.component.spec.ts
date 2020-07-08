import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { NucleoTestModule } from '../../../test.module';
import { VariavelMeteorologicaComponent } from 'app/entities/variavel-meteorologica/variavel-meteorologica.component';
import { VariavelMeteorologicaService } from 'app/entities/variavel-meteorologica/variavel-meteorologica.service';
import { VariavelMeteorologica } from 'app/shared/model/variavel-meteorologica.model';

describe('Component Tests', () => {
  describe('VariavelMeteorologica Management Component', () => {
    let comp: VariavelMeteorologicaComponent;
    let fixture: ComponentFixture<VariavelMeteorologicaComponent>;
    let service: VariavelMeteorologicaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [VariavelMeteorologicaComponent],
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
        .overrideTemplate(VariavelMeteorologicaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VariavelMeteorologicaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VariavelMeteorologicaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new VariavelMeteorologica(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.variavelMeteorologicas && comp.variavelMeteorologicas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new VariavelMeteorologica(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.variavelMeteorologicas && comp.variavelMeteorologicas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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

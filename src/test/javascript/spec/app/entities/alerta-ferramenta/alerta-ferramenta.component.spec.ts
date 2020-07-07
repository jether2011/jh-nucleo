import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { NucleoTestModule } from '../../../test.module';
import { AlertaFerramentaComponent } from 'app/entities/alerta-ferramenta/alerta-ferramenta.component';
import { AlertaFerramentaService } from 'app/entities/alerta-ferramenta/alerta-ferramenta.service';
import { AlertaFerramenta } from 'app/shared/model/alerta-ferramenta.model';

describe('Component Tests', () => {
  describe('AlertaFerramenta Management Component', () => {
    let comp: AlertaFerramentaComponent;
    let fixture: ComponentFixture<AlertaFerramentaComponent>;
    let service: AlertaFerramentaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AlertaFerramentaComponent],
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
        .overrideTemplate(AlertaFerramentaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlertaFerramentaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlertaFerramentaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AlertaFerramenta(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.alertaFerramentas && comp.alertaFerramentas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AlertaFerramenta(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.alertaFerramentas && comp.alertaFerramentas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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

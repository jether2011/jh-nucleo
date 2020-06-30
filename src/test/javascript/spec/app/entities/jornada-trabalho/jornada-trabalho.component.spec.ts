import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { NucleoTestModule } from '../../../test.module';
import { JornadaTrabalhoComponent } from 'app/entities/jornada-trabalho/jornada-trabalho.component';
import { JornadaTrabalhoService } from 'app/entities/jornada-trabalho/jornada-trabalho.service';
import { JornadaTrabalho } from 'app/shared/model/jornada-trabalho.model';

describe('Component Tests', () => {
  describe('JornadaTrabalho Management Component', () => {
    let comp: JornadaTrabalhoComponent;
    let fixture: ComponentFixture<JornadaTrabalhoComponent>;
    let service: JornadaTrabalhoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [JornadaTrabalhoComponent],
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
        .overrideTemplate(JornadaTrabalhoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JornadaTrabalhoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JornadaTrabalhoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new JornadaTrabalho(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.jornadaTrabalhos && comp.jornadaTrabalhos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new JornadaTrabalho(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.jornadaTrabalhos && comp.jornadaTrabalhos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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

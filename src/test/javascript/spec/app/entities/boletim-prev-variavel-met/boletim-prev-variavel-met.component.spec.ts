import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { NucleoTestModule } from '../../../test.module';
import { BoletimPrevVariavelMetComponent } from 'app/entities/boletim-prev-variavel-met/boletim-prev-variavel-met.component';
import { BoletimPrevVariavelMetService } from 'app/entities/boletim-prev-variavel-met/boletim-prev-variavel-met.service';
import { BoletimPrevVariavelMet } from 'app/shared/model/boletim-prev-variavel-met.model';

describe('Component Tests', () => {
  describe('BoletimPrevVariavelMet Management Component', () => {
    let comp: BoletimPrevVariavelMetComponent;
    let fixture: ComponentFixture<BoletimPrevVariavelMetComponent>;
    let service: BoletimPrevVariavelMetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [BoletimPrevVariavelMetComponent],
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
        .overrideTemplate(BoletimPrevVariavelMetComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BoletimPrevVariavelMetComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BoletimPrevVariavelMetService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BoletimPrevVariavelMet(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.boletimPrevVariavelMets && comp.boletimPrevVariavelMets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BoletimPrevVariavelMet(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.boletimPrevVariavelMets && comp.boletimPrevVariavelMets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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

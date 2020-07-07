import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { NucleoTestModule } from '../../../test.module';
import { BoletimPrevObsComponent } from 'app/entities/boletim-prev-obs/boletim-prev-obs.component';
import { BoletimPrevObsService } from 'app/entities/boletim-prev-obs/boletim-prev-obs.service';
import { BoletimPrevObs } from 'app/shared/model/boletim-prev-obs.model';

describe('Component Tests', () => {
  describe('BoletimPrevObs Management Component', () => {
    let comp: BoletimPrevObsComponent;
    let fixture: ComponentFixture<BoletimPrevObsComponent>;
    let service: BoletimPrevObsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [BoletimPrevObsComponent],
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
        .overrideTemplate(BoletimPrevObsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BoletimPrevObsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BoletimPrevObsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BoletimPrevObs(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.boletimPrevObs && comp.boletimPrevObs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BoletimPrevObs(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.boletimPrevObs && comp.boletimPrevObs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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

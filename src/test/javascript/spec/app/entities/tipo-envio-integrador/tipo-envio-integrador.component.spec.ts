import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { NucleoTestModule } from '../../../test.module';
import { TipoEnvioIntegradorComponent } from 'app/entities/tipo-envio-integrador/tipo-envio-integrador.component';
import { TipoEnvioIntegradorService } from 'app/entities/tipo-envio-integrador/tipo-envio-integrador.service';
import { TipoEnvioIntegrador } from 'app/shared/model/tipo-envio-integrador.model';

describe('Component Tests', () => {
  describe('TipoEnvioIntegrador Management Component', () => {
    let comp: TipoEnvioIntegradorComponent;
    let fixture: ComponentFixture<TipoEnvioIntegradorComponent>;
    let service: TipoEnvioIntegradorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [TipoEnvioIntegradorComponent],
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
        .overrideTemplate(TipoEnvioIntegradorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoEnvioIntegradorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoEnvioIntegradorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoEnvioIntegrador(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoEnvioIntegradors && comp.tipoEnvioIntegradors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoEnvioIntegrador(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoEnvioIntegradors && comp.tipoEnvioIntegradors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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

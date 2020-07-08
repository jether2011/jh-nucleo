import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { NucleoTestModule } from '../../../test.module';
import { PlanoRecursoTipoEnvioComponent } from 'app/entities/plano-recurso-tipo-envio/plano-recurso-tipo-envio.component';
import { PlanoRecursoTipoEnvioService } from 'app/entities/plano-recurso-tipo-envio/plano-recurso-tipo-envio.service';
import { PlanoRecursoTipoEnvio } from 'app/shared/model/plano-recurso-tipo-envio.model';

describe('Component Tests', () => {
  describe('PlanoRecursoTipoEnvio Management Component', () => {
    let comp: PlanoRecursoTipoEnvioComponent;
    let fixture: ComponentFixture<PlanoRecursoTipoEnvioComponent>;
    let service: PlanoRecursoTipoEnvioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [PlanoRecursoTipoEnvioComponent],
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
        .overrideTemplate(PlanoRecursoTipoEnvioComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlanoRecursoTipoEnvioComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlanoRecursoTipoEnvioService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PlanoRecursoTipoEnvio(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.planoRecursoTipoEnvios && comp.planoRecursoTipoEnvios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PlanoRecursoTipoEnvio(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.planoRecursoTipoEnvios && comp.planoRecursoTipoEnvios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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

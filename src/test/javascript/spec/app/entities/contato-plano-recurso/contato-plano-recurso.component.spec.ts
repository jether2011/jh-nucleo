import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { NucleoTestModule } from '../../../test.module';
import { ContatoPlanoRecursoComponent } from 'app/entities/contato-plano-recurso/contato-plano-recurso.component';
import { ContatoPlanoRecursoService } from 'app/entities/contato-plano-recurso/contato-plano-recurso.service';
import { ContatoPlanoRecurso } from 'app/shared/model/contato-plano-recurso.model';

describe('Component Tests', () => {
  describe('ContatoPlanoRecurso Management Component', () => {
    let comp: ContatoPlanoRecursoComponent;
    let fixture: ComponentFixture<ContatoPlanoRecursoComponent>;
    let service: ContatoPlanoRecursoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [ContatoPlanoRecursoComponent],
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
        .overrideTemplate(ContatoPlanoRecursoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContatoPlanoRecursoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContatoPlanoRecursoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ContatoPlanoRecurso(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.contatoPlanoRecursos && comp.contatoPlanoRecursos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ContatoPlanoRecurso(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.contatoPlanoRecursos && comp.contatoPlanoRecursos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { NucleoTestModule } from '../../../test.module';
import { NotificacaoEnviadaComponent } from 'app/entities/notificacao-enviada/notificacao-enviada.component';
import { NotificacaoEnviadaService } from 'app/entities/notificacao-enviada/notificacao-enviada.service';
import { NotificacaoEnviada } from 'app/shared/model/notificacao-enviada.model';

describe('Component Tests', () => {
  describe('NotificacaoEnviada Management Component', () => {
    let comp: NotificacaoEnviadaComponent;
    let fixture: ComponentFixture<NotificacaoEnviadaComponent>;
    let service: NotificacaoEnviadaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [NotificacaoEnviadaComponent],
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
        .overrideTemplate(NotificacaoEnviadaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NotificacaoEnviadaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NotificacaoEnviadaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new NotificacaoEnviada(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.notificacaoEnviadas && comp.notificacaoEnviadas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new NotificacaoEnviada(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.notificacaoEnviadas && comp.notificacaoEnviadas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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

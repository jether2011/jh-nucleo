import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { NotificacaoEnviadaDetailComponent } from 'app/entities/notificacao-enviada/notificacao-enviada-detail.component';
import { NotificacaoEnviada } from 'app/shared/model/notificacao-enviada.model';

describe('Component Tests', () => {
  describe('NotificacaoEnviada Management Detail Component', () => {
    let comp: NotificacaoEnviadaDetailComponent;
    let fixture: ComponentFixture<NotificacaoEnviadaDetailComponent>;
    const route = ({ data: of({ notificacaoEnviada: new NotificacaoEnviada(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [NotificacaoEnviadaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(NotificacaoEnviadaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NotificacaoEnviadaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load notificacaoEnviada on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.notificacaoEnviada).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

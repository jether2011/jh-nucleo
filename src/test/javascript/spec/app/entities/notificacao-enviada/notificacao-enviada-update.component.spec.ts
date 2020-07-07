import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { NotificacaoEnviadaUpdateComponent } from 'app/entities/notificacao-enviada/notificacao-enviada-update.component';
import { NotificacaoEnviadaService } from 'app/entities/notificacao-enviada/notificacao-enviada.service';
import { NotificacaoEnviada } from 'app/shared/model/notificacao-enviada.model';

describe('Component Tests', () => {
  describe('NotificacaoEnviada Management Update Component', () => {
    let comp: NotificacaoEnviadaUpdateComponent;
    let fixture: ComponentFixture<NotificacaoEnviadaUpdateComponent>;
    let service: NotificacaoEnviadaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [NotificacaoEnviadaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(NotificacaoEnviadaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NotificacaoEnviadaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NotificacaoEnviadaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NotificacaoEnviada(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new NotificacaoEnviada();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

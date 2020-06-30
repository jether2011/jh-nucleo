import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { ContatoAlvoUpdateComponent } from 'app/entities/contato-alvo/contato-alvo-update.component';
import { ContatoAlvoService } from 'app/entities/contato-alvo/contato-alvo.service';
import { ContatoAlvo } from 'app/shared/model/contato-alvo.model';

describe('Component Tests', () => {
  describe('ContatoAlvo Management Update Component', () => {
    let comp: ContatoAlvoUpdateComponent;
    let fixture: ComponentFixture<ContatoAlvoUpdateComponent>;
    let service: ContatoAlvoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [ContatoAlvoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ContatoAlvoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContatoAlvoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContatoAlvoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContatoAlvo(123);
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
        const entity = new ContatoAlvo();
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

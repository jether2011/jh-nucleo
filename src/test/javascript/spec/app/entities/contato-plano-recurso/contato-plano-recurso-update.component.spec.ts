import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { ContatoPlanoRecursoUpdateComponent } from 'app/entities/contato-plano-recurso/contato-plano-recurso-update.component';
import { ContatoPlanoRecursoService } from 'app/entities/contato-plano-recurso/contato-plano-recurso.service';
import { ContatoPlanoRecurso } from 'app/shared/model/contato-plano-recurso.model';

describe('Component Tests', () => {
  describe('ContatoPlanoRecurso Management Update Component', () => {
    let comp: ContatoPlanoRecursoUpdateComponent;
    let fixture: ComponentFixture<ContatoPlanoRecursoUpdateComponent>;
    let service: ContatoPlanoRecursoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [ContatoPlanoRecursoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ContatoPlanoRecursoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContatoPlanoRecursoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContatoPlanoRecursoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContatoPlanoRecurso(123);
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
        const entity = new ContatoPlanoRecurso();
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

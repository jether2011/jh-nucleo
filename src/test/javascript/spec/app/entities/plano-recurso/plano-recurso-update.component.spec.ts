import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { PlanoRecursoUpdateComponent } from 'app/entities/plano-recurso/plano-recurso-update.component';
import { PlanoRecursoService } from 'app/entities/plano-recurso/plano-recurso.service';
import { PlanoRecurso } from 'app/shared/model/plano-recurso.model';

describe('Component Tests', () => {
  describe('PlanoRecurso Management Update Component', () => {
    let comp: PlanoRecursoUpdateComponent;
    let fixture: ComponentFixture<PlanoRecursoUpdateComponent>;
    let service: PlanoRecursoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [PlanoRecursoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PlanoRecursoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlanoRecursoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlanoRecursoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlanoRecurso(123);
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
        const entity = new PlanoRecurso();
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

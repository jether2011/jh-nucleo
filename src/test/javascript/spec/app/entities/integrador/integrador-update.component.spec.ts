import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { IntegradorUpdateComponent } from 'app/entities/integrador/integrador-update.component';
import { IntegradorService } from 'app/entities/integrador/integrador.service';
import { Integrador } from 'app/shared/model/integrador.model';

describe('Component Tests', () => {
  describe('Integrador Management Update Component', () => {
    let comp: IntegradorUpdateComponent;
    let fixture: ComponentFixture<IntegradorUpdateComponent>;
    let service: IntegradorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [IntegradorUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IntegradorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IntegradorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IntegradorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Integrador(123);
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
        const entity = new Integrador();
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

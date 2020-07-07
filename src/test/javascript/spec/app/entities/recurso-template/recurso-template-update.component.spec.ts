import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { RecursoTemplateUpdateComponent } from 'app/entities/recurso-template/recurso-template-update.component';
import { RecursoTemplateService } from 'app/entities/recurso-template/recurso-template.service';
import { RecursoTemplate } from 'app/shared/model/recurso-template.model';

describe('Component Tests', () => {
  describe('RecursoTemplate Management Update Component', () => {
    let comp: RecursoTemplateUpdateComponent;
    let fixture: ComponentFixture<RecursoTemplateUpdateComponent>;
    let service: RecursoTemplateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [RecursoTemplateUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RecursoTemplateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecursoTemplateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecursoTemplateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RecursoTemplate(123);
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
        const entity = new RecursoTemplate();
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

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { RecursoTipoUpdateComponent } from 'app/entities/recurso-tipo/recurso-tipo-update.component';
import { RecursoTipoService } from 'app/entities/recurso-tipo/recurso-tipo.service';
import { RecursoTipo } from 'app/shared/model/recurso-tipo.model';

describe('Component Tests', () => {
  describe('RecursoTipo Management Update Component', () => {
    let comp: RecursoTipoUpdateComponent;
    let fixture: ComponentFixture<RecursoTipoUpdateComponent>;
    let service: RecursoTipoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [RecursoTipoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RecursoTipoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecursoTipoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecursoTipoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RecursoTipo(123);
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
        const entity = new RecursoTipo();
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

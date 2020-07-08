import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AlertaTipoUpdateComponent } from 'app/entities/alerta-tipo/alerta-tipo-update.component';
import { AlertaTipoService } from 'app/entities/alerta-tipo/alerta-tipo.service';
import { AlertaTipo } from 'app/shared/model/alerta-tipo.model';

describe('Component Tests', () => {
  describe('AlertaTipo Management Update Component', () => {
    let comp: AlertaTipoUpdateComponent;
    let fixture: ComponentFixture<AlertaTipoUpdateComponent>;
    let service: AlertaTipoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AlertaTipoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AlertaTipoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlertaTipoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlertaTipoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AlertaTipo(123);
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
        const entity = new AlertaTipo();
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

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AlertaUpdateComponent } from 'app/entities/alerta/alerta-update.component';
import { AlertaService } from 'app/entities/alerta/alerta.service';
import { Alerta } from 'app/shared/model/alerta.model';

describe('Component Tests', () => {
  describe('Alerta Management Update Component', () => {
    let comp: AlertaUpdateComponent;
    let fixture: ComponentFixture<AlertaUpdateComponent>;
    let service: AlertaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AlertaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AlertaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlertaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlertaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Alerta(123);
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
        const entity = new Alerta();
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

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AlertaRiscoUpdateComponent } from 'app/entities/alerta-risco/alerta-risco-update.component';
import { AlertaRiscoService } from 'app/entities/alerta-risco/alerta-risco.service';
import { AlertaRisco } from 'app/shared/model/alerta-risco.model';

describe('Component Tests', () => {
  describe('AlertaRisco Management Update Component', () => {
    let comp: AlertaRiscoUpdateComponent;
    let fixture: ComponentFixture<AlertaRiscoUpdateComponent>;
    let service: AlertaRiscoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AlertaRiscoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AlertaRiscoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlertaRiscoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlertaRiscoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AlertaRisco(123);
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
        const entity = new AlertaRisco();
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

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { PlanoStatusUpdateComponent } from 'app/entities/plano-status/plano-status-update.component';
import { PlanoStatusService } from 'app/entities/plano-status/plano-status.service';
import { PlanoStatus } from 'app/shared/model/plano-status.model';

describe('Component Tests', () => {
  describe('PlanoStatus Management Update Component', () => {
    let comp: PlanoStatusUpdateComponent;
    let fixture: ComponentFixture<PlanoStatusUpdateComponent>;
    let service: PlanoStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [PlanoStatusUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PlanoStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlanoStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlanoStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlanoStatus(123);
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
        const entity = new PlanoStatus();
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

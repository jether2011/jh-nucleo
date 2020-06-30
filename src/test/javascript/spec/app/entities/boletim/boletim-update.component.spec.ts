import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { BoletimUpdateComponent } from 'app/entities/boletim/boletim-update.component';
import { BoletimService } from 'app/entities/boletim/boletim.service';
import { Boletim } from 'app/shared/model/boletim.model';

describe('Component Tests', () => {
  describe('Boletim Management Update Component', () => {
    let comp: BoletimUpdateComponent;
    let fixture: ComponentFixture<BoletimUpdateComponent>;
    let service: BoletimService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [BoletimUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BoletimUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BoletimUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BoletimService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Boletim(123);
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
        const entity = new Boletim();
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

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { BoletimPrevObsUpdateComponent } from 'app/entities/boletim-prev-obs/boletim-prev-obs-update.component';
import { BoletimPrevObsService } from 'app/entities/boletim-prev-obs/boletim-prev-obs.service';
import { BoletimPrevObs } from 'app/shared/model/boletim-prev-obs.model';

describe('Component Tests', () => {
  describe('BoletimPrevObs Management Update Component', () => {
    let comp: BoletimPrevObsUpdateComponent;
    let fixture: ComponentFixture<BoletimPrevObsUpdateComponent>;
    let service: BoletimPrevObsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [BoletimPrevObsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BoletimPrevObsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BoletimPrevObsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BoletimPrevObsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BoletimPrevObs(123);
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
        const entity = new BoletimPrevObs();
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

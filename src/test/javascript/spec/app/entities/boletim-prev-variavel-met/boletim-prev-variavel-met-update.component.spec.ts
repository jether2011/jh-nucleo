import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { BoletimPrevVariavelMetUpdateComponent } from 'app/entities/boletim-prev-variavel-met/boletim-prev-variavel-met-update.component';
import { BoletimPrevVariavelMetService } from 'app/entities/boletim-prev-variavel-met/boletim-prev-variavel-met.service';
import { BoletimPrevVariavelMet } from 'app/shared/model/boletim-prev-variavel-met.model';

describe('Component Tests', () => {
  describe('BoletimPrevVariavelMet Management Update Component', () => {
    let comp: BoletimPrevVariavelMetUpdateComponent;
    let fixture: ComponentFixture<BoletimPrevVariavelMetUpdateComponent>;
    let service: BoletimPrevVariavelMetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [BoletimPrevVariavelMetUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BoletimPrevVariavelMetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BoletimPrevVariavelMetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BoletimPrevVariavelMetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BoletimPrevVariavelMet(123);
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
        const entity = new BoletimPrevVariavelMet();
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

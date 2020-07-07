import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { IntensidadeChuvaUpdateComponent } from 'app/entities/intensidade-chuva/intensidade-chuva-update.component';
import { IntensidadeChuvaService } from 'app/entities/intensidade-chuva/intensidade-chuva.service';
import { IntensidadeChuva } from 'app/shared/model/intensidade-chuva.model';

describe('Component Tests', () => {
  describe('IntensidadeChuva Management Update Component', () => {
    let comp: IntensidadeChuvaUpdateComponent;
    let fixture: ComponentFixture<IntensidadeChuvaUpdateComponent>;
    let service: IntensidadeChuvaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [IntensidadeChuvaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IntensidadeChuvaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IntensidadeChuvaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IntensidadeChuvaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IntensidadeChuva(123);
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
        const entity = new IntensidadeChuva();
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

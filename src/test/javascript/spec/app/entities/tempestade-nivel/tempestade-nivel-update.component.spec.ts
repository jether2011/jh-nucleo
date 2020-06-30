import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { TempestadeNivelUpdateComponent } from 'app/entities/tempestade-nivel/tempestade-nivel-update.component';
import { TempestadeNivelService } from 'app/entities/tempestade-nivel/tempestade-nivel.service';
import { TempestadeNivel } from 'app/shared/model/tempestade-nivel.model';

describe('Component Tests', () => {
  describe('TempestadeNivel Management Update Component', () => {
    let comp: TempestadeNivelUpdateComponent;
    let fixture: ComponentFixture<TempestadeNivelUpdateComponent>;
    let service: TempestadeNivelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [TempestadeNivelUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TempestadeNivelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TempestadeNivelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TempestadeNivelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TempestadeNivel(123);
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
        const entity = new TempestadeNivel();
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

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { VariavelMeteorologicaUpdateComponent } from 'app/entities/variavel-meteorologica/variavel-meteorologica-update.component';
import { VariavelMeteorologicaService } from 'app/entities/variavel-meteorologica/variavel-meteorologica.service';
import { VariavelMeteorologica } from 'app/shared/model/variavel-meteorologica.model';

describe('Component Tests', () => {
  describe('VariavelMeteorologica Management Update Component', () => {
    let comp: VariavelMeteorologicaUpdateComponent;
    let fixture: ComponentFixture<VariavelMeteorologicaUpdateComponent>;
    let service: VariavelMeteorologicaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [VariavelMeteorologicaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(VariavelMeteorologicaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VariavelMeteorologicaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VariavelMeteorologicaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VariavelMeteorologica(123);
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
        const entity = new VariavelMeteorologica();
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

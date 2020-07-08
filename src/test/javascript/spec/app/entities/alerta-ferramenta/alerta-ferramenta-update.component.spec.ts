import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AlertaFerramentaUpdateComponent } from 'app/entities/alerta-ferramenta/alerta-ferramenta-update.component';
import { AlertaFerramentaService } from 'app/entities/alerta-ferramenta/alerta-ferramenta.service';
import { AlertaFerramenta } from 'app/shared/model/alerta-ferramenta.model';

describe('Component Tests', () => {
  describe('AlertaFerramenta Management Update Component', () => {
    let comp: AlertaFerramentaUpdateComponent;
    let fixture: ComponentFixture<AlertaFerramentaUpdateComponent>;
    let service: AlertaFerramentaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AlertaFerramentaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AlertaFerramentaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlertaFerramentaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlertaFerramentaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AlertaFerramenta(123);
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
        const entity = new AlertaFerramenta();
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

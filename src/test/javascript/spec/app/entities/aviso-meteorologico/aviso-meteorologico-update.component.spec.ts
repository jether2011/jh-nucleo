import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AvisoMeteorologicoUpdateComponent } from 'app/entities/aviso-meteorologico/aviso-meteorologico-update.component';
import { AvisoMeteorologicoService } from 'app/entities/aviso-meteorologico/aviso-meteorologico.service';
import { AvisoMeteorologico } from 'app/shared/model/aviso-meteorologico.model';

describe('Component Tests', () => {
  describe('AvisoMeteorologico Management Update Component', () => {
    let comp: AvisoMeteorologicoUpdateComponent;
    let fixture: ComponentFixture<AvisoMeteorologicoUpdateComponent>;
    let service: AvisoMeteorologicoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AvisoMeteorologicoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AvisoMeteorologicoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvisoMeteorologicoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvisoMeteorologicoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AvisoMeteorologico(123);
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
        const entity = new AvisoMeteorologico();
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

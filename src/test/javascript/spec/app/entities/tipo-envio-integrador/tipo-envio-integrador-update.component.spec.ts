import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { TipoEnvioIntegradorUpdateComponent } from 'app/entities/tipo-envio-integrador/tipo-envio-integrador-update.component';
import { TipoEnvioIntegradorService } from 'app/entities/tipo-envio-integrador/tipo-envio-integrador.service';
import { TipoEnvioIntegrador } from 'app/shared/model/tipo-envio-integrador.model';

describe('Component Tests', () => {
  describe('TipoEnvioIntegrador Management Update Component', () => {
    let comp: TipoEnvioIntegradorUpdateComponent;
    let fixture: ComponentFixture<TipoEnvioIntegradorUpdateComponent>;
    let service: TipoEnvioIntegradorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [TipoEnvioIntegradorUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TipoEnvioIntegradorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoEnvioIntegradorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoEnvioIntegradorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoEnvioIntegrador(123);
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
        const entity = new TipoEnvioIntegrador();
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

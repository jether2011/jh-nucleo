import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { TipoEnvioUpdateComponent } from 'app/entities/tipo-envio/tipo-envio-update.component';
import { TipoEnvioService } from 'app/entities/tipo-envio/tipo-envio.service';
import { TipoEnvio } from 'app/shared/model/tipo-envio.model';

describe('Component Tests', () => {
  describe('TipoEnvio Management Update Component', () => {
    let comp: TipoEnvioUpdateComponent;
    let fixture: ComponentFixture<TipoEnvioUpdateComponent>;
    let service: TipoEnvioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [TipoEnvioUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TipoEnvioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoEnvioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoEnvioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoEnvio(123);
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
        const entity = new TipoEnvio();
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

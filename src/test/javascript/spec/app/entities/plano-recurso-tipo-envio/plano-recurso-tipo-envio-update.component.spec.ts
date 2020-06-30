import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { PlanoRecursoTipoEnvioUpdateComponent } from 'app/entities/plano-recurso-tipo-envio/plano-recurso-tipo-envio-update.component';
import { PlanoRecursoTipoEnvioService } from 'app/entities/plano-recurso-tipo-envio/plano-recurso-tipo-envio.service';
import { PlanoRecursoTipoEnvio } from 'app/shared/model/plano-recurso-tipo-envio.model';

describe('Component Tests', () => {
  describe('PlanoRecursoTipoEnvio Management Update Component', () => {
    let comp: PlanoRecursoTipoEnvioUpdateComponent;
    let fixture: ComponentFixture<PlanoRecursoTipoEnvioUpdateComponent>;
    let service: PlanoRecursoTipoEnvioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [PlanoRecursoTipoEnvioUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PlanoRecursoTipoEnvioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlanoRecursoTipoEnvioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlanoRecursoTipoEnvioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlanoRecursoTipoEnvio(123);
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
        const entity = new PlanoRecursoTipoEnvio();
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

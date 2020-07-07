import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { ContatoTipoEnvioUpdateComponent } from 'app/entities/contato-tipo-envio/contato-tipo-envio-update.component';
import { ContatoTipoEnvioService } from 'app/entities/contato-tipo-envio/contato-tipo-envio.service';
import { ContatoTipoEnvio } from 'app/shared/model/contato-tipo-envio.model';

describe('Component Tests', () => {
  describe('ContatoTipoEnvio Management Update Component', () => {
    let comp: ContatoTipoEnvioUpdateComponent;
    let fixture: ComponentFixture<ContatoTipoEnvioUpdateComponent>;
    let service: ContatoTipoEnvioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [ContatoTipoEnvioUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ContatoTipoEnvioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContatoTipoEnvioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContatoTipoEnvioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContatoTipoEnvio(123);
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
        const entity = new ContatoTipoEnvio();
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

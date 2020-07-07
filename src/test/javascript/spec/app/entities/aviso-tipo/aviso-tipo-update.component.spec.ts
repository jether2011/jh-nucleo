import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AvisoTipoUpdateComponent } from 'app/entities/aviso-tipo/aviso-tipo-update.component';
import { AvisoTipoService } from 'app/entities/aviso-tipo/aviso-tipo.service';
import { AvisoTipo } from 'app/shared/model/aviso-tipo.model';

describe('Component Tests', () => {
  describe('AvisoTipo Management Update Component', () => {
    let comp: AvisoTipoUpdateComponent;
    let fixture: ComponentFixture<AvisoTipoUpdateComponent>;
    let service: AvisoTipoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AvisoTipoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AvisoTipoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvisoTipoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvisoTipoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AvisoTipo(123);
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
        const entity = new AvisoTipo();
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

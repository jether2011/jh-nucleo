import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AvisoUpdateComponent } from 'app/entities/aviso/aviso-update.component';
import { AvisoService } from 'app/entities/aviso/aviso.service';
import { Aviso } from 'app/shared/model/aviso.model';

describe('Component Tests', () => {
  describe('Aviso Management Update Component', () => {
    let comp: AvisoUpdateComponent;
    let fixture: ComponentFixture<AvisoUpdateComponent>;
    let service: AvisoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AvisoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AvisoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvisoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvisoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Aviso(123);
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
        const entity = new Aviso();
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

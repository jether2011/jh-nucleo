import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { FerramentaUpdateComponent } from 'app/entities/ferramenta/ferramenta-update.component';
import { FerramentaService } from 'app/entities/ferramenta/ferramenta.service';
import { Ferramenta } from 'app/shared/model/ferramenta.model';

describe('Component Tests', () => {
  describe('Ferramenta Management Update Component', () => {
    let comp: FerramentaUpdateComponent;
    let fixture: ComponentFixture<FerramentaUpdateComponent>;
    let service: FerramentaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [FerramentaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FerramentaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FerramentaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FerramentaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Ferramenta(123);
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
        const entity = new Ferramenta();
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

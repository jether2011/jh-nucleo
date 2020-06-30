import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { VoceSabiaUpdateComponent } from 'app/entities/voce-sabia/voce-sabia-update.component';
import { VoceSabiaService } from 'app/entities/voce-sabia/voce-sabia.service';
import { VoceSabia } from 'app/shared/model/voce-sabia.model';

describe('Component Tests', () => {
  describe('VoceSabia Management Update Component', () => {
    let comp: VoceSabiaUpdateComponent;
    let fixture: ComponentFixture<VoceSabiaUpdateComponent>;
    let service: VoceSabiaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [VoceSabiaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(VoceSabiaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VoceSabiaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VoceSabiaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VoceSabia(123);
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
        const entity = new VoceSabia();
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

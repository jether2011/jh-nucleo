import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { UmidadeArUpdateComponent } from 'app/entities/umidade-ar/umidade-ar-update.component';
import { UmidadeArService } from 'app/entities/umidade-ar/umidade-ar.service';
import { UmidadeAr } from 'app/shared/model/umidade-ar.model';

describe('Component Tests', () => {
  describe('UmidadeAr Management Update Component', () => {
    let comp: UmidadeArUpdateComponent;
    let fixture: ComponentFixture<UmidadeArUpdateComponent>;
    let service: UmidadeArService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [UmidadeArUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UmidadeArUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UmidadeArUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UmidadeArService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UmidadeAr(123);
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
        const entity = new UmidadeAr();
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

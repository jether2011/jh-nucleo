import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { DescargaUpdateComponent } from 'app/entities/descarga/descarga-update.component';
import { DescargaService } from 'app/entities/descarga/descarga.service';
import { Descarga } from 'app/shared/model/descarga.model';

describe('Component Tests', () => {
  describe('Descarga Management Update Component', () => {
    let comp: DescargaUpdateComponent;
    let fixture: ComponentFixture<DescargaUpdateComponent>;
    let service: DescargaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [DescargaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DescargaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DescargaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DescargaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Descarga(123);
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
        const entity = new Descarga();
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

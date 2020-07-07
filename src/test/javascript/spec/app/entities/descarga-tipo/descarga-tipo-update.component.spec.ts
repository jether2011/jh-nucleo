import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { DescargaTipoUpdateComponent } from 'app/entities/descarga-tipo/descarga-tipo-update.component';
import { DescargaTipoService } from 'app/entities/descarga-tipo/descarga-tipo.service';
import { DescargaTipo } from 'app/shared/model/descarga-tipo.model';

describe('Component Tests', () => {
  describe('DescargaTipo Management Update Component', () => {
    let comp: DescargaTipoUpdateComponent;
    let fixture: ComponentFixture<DescargaTipoUpdateComponent>;
    let service: DescargaTipoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [DescargaTipoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DescargaTipoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DescargaTipoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DescargaTipoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DescargaTipo(123);
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
        const entity = new DescargaTipo();
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

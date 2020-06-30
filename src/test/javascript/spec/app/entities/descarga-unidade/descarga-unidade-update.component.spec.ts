import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { DescargaUnidadeUpdateComponent } from 'app/entities/descarga-unidade/descarga-unidade-update.component';
import { DescargaUnidadeService } from 'app/entities/descarga-unidade/descarga-unidade.service';
import { DescargaUnidade } from 'app/shared/model/descarga-unidade.model';

describe('Component Tests', () => {
  describe('DescargaUnidade Management Update Component', () => {
    let comp: DescargaUnidadeUpdateComponent;
    let fixture: ComponentFixture<DescargaUnidadeUpdateComponent>;
    let service: DescargaUnidadeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [DescargaUnidadeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DescargaUnidadeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DescargaUnidadeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DescargaUnidadeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DescargaUnidade(123);
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
        const entity = new DescargaUnidade();
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

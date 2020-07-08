import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { PrevisaoUpdateComponent } from 'app/entities/previsao/previsao-update.component';
import { PrevisaoService } from 'app/entities/previsao/previsao.service';
import { Previsao } from 'app/shared/model/previsao.model';

describe('Component Tests', () => {
  describe('Previsao Management Update Component', () => {
    let comp: PrevisaoUpdateComponent;
    let fixture: ComponentFixture<PrevisaoUpdateComponent>;
    let service: PrevisaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [PrevisaoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PrevisaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrevisaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrevisaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Previsao(123);
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
        const entity = new Previsao();
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

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { TempestadeProbabilidadeUpdateComponent } from 'app/entities/tempestade-probabilidade/tempestade-probabilidade-update.component';
import { TempestadeProbabilidadeService } from 'app/entities/tempestade-probabilidade/tempestade-probabilidade.service';
import { TempestadeProbabilidade } from 'app/shared/model/tempestade-probabilidade.model';

describe('Component Tests', () => {
  describe('TempestadeProbabilidade Management Update Component', () => {
    let comp: TempestadeProbabilidadeUpdateComponent;
    let fixture: ComponentFixture<TempestadeProbabilidadeUpdateComponent>;
    let service: TempestadeProbabilidadeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [TempestadeProbabilidadeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TempestadeProbabilidadeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TempestadeProbabilidadeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TempestadeProbabilidadeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TempestadeProbabilidade(123);
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
        const entity = new TempestadeProbabilidade();
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

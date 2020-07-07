import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { TipoFerramentaUpdateComponent } from 'app/entities/tipo-ferramenta/tipo-ferramenta-update.component';
import { TipoFerramentaService } from 'app/entities/tipo-ferramenta/tipo-ferramenta.service';
import { TipoFerramenta } from 'app/shared/model/tipo-ferramenta.model';

describe('Component Tests', () => {
  describe('TipoFerramenta Management Update Component', () => {
    let comp: TipoFerramentaUpdateComponent;
    let fixture: ComponentFixture<TipoFerramentaUpdateComponent>;
    let service: TipoFerramentaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [TipoFerramentaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TipoFerramentaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoFerramentaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoFerramentaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoFerramenta(123);
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
        const entity = new TipoFerramenta();
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

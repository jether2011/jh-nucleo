import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { PontosCardeaisUpdateComponent } from 'app/entities/pontos-cardeais/pontos-cardeais-update.component';
import { PontosCardeaisService } from 'app/entities/pontos-cardeais/pontos-cardeais.service';
import { PontosCardeais } from 'app/shared/model/pontos-cardeais.model';

describe('Component Tests', () => {
  describe('PontosCardeais Management Update Component', () => {
    let comp: PontosCardeaisUpdateComponent;
    let fixture: ComponentFixture<PontosCardeaisUpdateComponent>;
    let service: PontosCardeaisService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [PontosCardeaisUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PontosCardeaisUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PontosCardeaisUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PontosCardeaisService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PontosCardeais(123);
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
        const entity = new PontosCardeais();
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

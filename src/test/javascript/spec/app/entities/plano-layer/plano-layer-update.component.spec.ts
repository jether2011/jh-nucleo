import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { PlanoLayerUpdateComponent } from 'app/entities/plano-layer/plano-layer-update.component';
import { PlanoLayerService } from 'app/entities/plano-layer/plano-layer.service';
import { PlanoLayer } from 'app/shared/model/plano-layer.model';

describe('Component Tests', () => {
  describe('PlanoLayer Management Update Component', () => {
    let comp: PlanoLayerUpdateComponent;
    let fixture: ComponentFixture<PlanoLayerUpdateComponent>;
    let service: PlanoLayerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [PlanoLayerUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PlanoLayerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlanoLayerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlanoLayerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlanoLayer(123);
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
        const entity = new PlanoLayer();
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

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { LayerUpdateComponent } from 'app/entities/layer/layer-update.component';
import { LayerService } from 'app/entities/layer/layer.service';
import { Layer } from 'app/shared/model/layer.model';

describe('Component Tests', () => {
  describe('Layer Management Update Component', () => {
    let comp: LayerUpdateComponent;
    let fixture: ComponentFixture<LayerUpdateComponent>;
    let service: LayerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [LayerUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LayerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LayerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LayerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Layer(123);
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
        const entity = new Layer();
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

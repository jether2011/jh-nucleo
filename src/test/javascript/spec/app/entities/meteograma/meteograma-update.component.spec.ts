import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { MeteogramaUpdateComponent } from 'app/entities/meteograma/meteograma-update.component';
import { MeteogramaService } from 'app/entities/meteograma/meteograma.service';
import { Meteograma } from 'app/shared/model/meteograma.model';

describe('Component Tests', () => {
  describe('Meteograma Management Update Component', () => {
    let comp: MeteogramaUpdateComponent;
    let fixture: ComponentFixture<MeteogramaUpdateComponent>;
    let service: MeteogramaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [MeteogramaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MeteogramaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MeteogramaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MeteogramaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Meteograma(123);
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
        const entity = new Meteograma();
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

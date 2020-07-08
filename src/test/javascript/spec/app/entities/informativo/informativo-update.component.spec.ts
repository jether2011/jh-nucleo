import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { InformativoUpdateComponent } from 'app/entities/informativo/informativo-update.component';
import { InformativoService } from 'app/entities/informativo/informativo.service';
import { Informativo } from 'app/shared/model/informativo.model';

describe('Component Tests', () => {
  describe('Informativo Management Update Component', () => {
    let comp: InformativoUpdateComponent;
    let fixture: ComponentFixture<InformativoUpdateComponent>;
    let service: InformativoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [InformativoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(InformativoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InformativoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InformativoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Informativo(123);
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
        const entity = new Informativo();
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

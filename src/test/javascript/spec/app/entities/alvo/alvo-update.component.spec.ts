import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AlvoUpdateComponent } from 'app/entities/alvo/alvo-update.component';
import { AlvoService } from 'app/entities/alvo/alvo.service';
import { Alvo } from 'app/shared/model/alvo.model';

describe('Component Tests', () => {
  describe('Alvo Management Update Component', () => {
    let comp: AlvoUpdateComponent;
    let fixture: ComponentFixture<AlvoUpdateComponent>;
    let service: AlvoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AlvoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AlvoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlvoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlvoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Alvo(123);
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
        const entity = new Alvo();
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

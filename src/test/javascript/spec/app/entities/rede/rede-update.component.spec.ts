import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { RedeUpdateComponent } from 'app/entities/rede/rede-update.component';
import { RedeService } from 'app/entities/rede/rede.service';
import { Rede } from 'app/shared/model/rede.model';

describe('Component Tests', () => {
  describe('Rede Management Update Component', () => {
    let comp: RedeUpdateComponent;
    let fixture: ComponentFixture<RedeUpdateComponent>;
    let service: RedeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [RedeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RedeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RedeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RedeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Rede(123);
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
        const entity = new Rede();
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

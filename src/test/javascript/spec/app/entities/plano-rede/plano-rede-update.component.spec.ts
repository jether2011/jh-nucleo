import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { PlanoRedeUpdateComponent } from 'app/entities/plano-rede/plano-rede-update.component';
import { PlanoRedeService } from 'app/entities/plano-rede/plano-rede.service';
import { PlanoRede } from 'app/shared/model/plano-rede.model';

describe('Component Tests', () => {
  describe('PlanoRede Management Update Component', () => {
    let comp: PlanoRedeUpdateComponent;
    let fixture: ComponentFixture<PlanoRedeUpdateComponent>;
    let service: PlanoRedeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [PlanoRedeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PlanoRedeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlanoRedeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlanoRedeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlanoRede(123);
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
        const entity = new PlanoRede();
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

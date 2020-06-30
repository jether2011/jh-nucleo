import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { BoletimPrevisaoUpdateComponent } from 'app/entities/boletim-previsao/boletim-previsao-update.component';
import { BoletimPrevisaoService } from 'app/entities/boletim-previsao/boletim-previsao.service';
import { BoletimPrevisao } from 'app/shared/model/boletim-previsao.model';

describe('Component Tests', () => {
  describe('BoletimPrevisao Management Update Component', () => {
    let comp: BoletimPrevisaoUpdateComponent;
    let fixture: ComponentFixture<BoletimPrevisaoUpdateComponent>;
    let service: BoletimPrevisaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [BoletimPrevisaoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BoletimPrevisaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BoletimPrevisaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BoletimPrevisaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BoletimPrevisao(123);
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
        const entity = new BoletimPrevisao();
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

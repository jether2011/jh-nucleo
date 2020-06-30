import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AcumuladoChuvaFaixaUpdateComponent } from 'app/entities/acumulado-chuva-faixa/acumulado-chuva-faixa-update.component';
import { AcumuladoChuvaFaixaService } from 'app/entities/acumulado-chuva-faixa/acumulado-chuva-faixa.service';
import { AcumuladoChuvaFaixa } from 'app/shared/model/acumulado-chuva-faixa.model';

describe('Component Tests', () => {
  describe('AcumuladoChuvaFaixa Management Update Component', () => {
    let comp: AcumuladoChuvaFaixaUpdateComponent;
    let fixture: ComponentFixture<AcumuladoChuvaFaixaUpdateComponent>;
    let service: AcumuladoChuvaFaixaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AcumuladoChuvaFaixaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AcumuladoChuvaFaixaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AcumuladoChuvaFaixaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AcumuladoChuvaFaixaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AcumuladoChuvaFaixa(123);
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
        const entity = new AcumuladoChuvaFaixa();
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

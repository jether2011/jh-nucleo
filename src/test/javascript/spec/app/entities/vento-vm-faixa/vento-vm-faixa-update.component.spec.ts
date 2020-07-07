import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { VentoVmFaixaUpdateComponent } from 'app/entities/vento-vm-faixa/vento-vm-faixa-update.component';
import { VentoVmFaixaService } from 'app/entities/vento-vm-faixa/vento-vm-faixa.service';
import { VentoVmFaixa } from 'app/shared/model/vento-vm-faixa.model';

describe('Component Tests', () => {
  describe('VentoVmFaixa Management Update Component', () => {
    let comp: VentoVmFaixaUpdateComponent;
    let fixture: ComponentFixture<VentoVmFaixaUpdateComponent>;
    let service: VentoVmFaixaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [VentoVmFaixaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(VentoVmFaixaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VentoVmFaixaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VentoVmFaixaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VentoVmFaixa(123);
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
        const entity = new VentoVmFaixa();
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

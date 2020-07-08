import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { CondicaoTempoUpdateComponent } from 'app/entities/condicao-tempo/condicao-tempo-update.component';
import { CondicaoTempoService } from 'app/entities/condicao-tempo/condicao-tempo.service';
import { CondicaoTempo } from 'app/shared/model/condicao-tempo.model';

describe('Component Tests', () => {
  describe('CondicaoTempo Management Update Component', () => {
    let comp: CondicaoTempoUpdateComponent;
    let fixture: ComponentFixture<CondicaoTempoUpdateComponent>;
    let service: CondicaoTempoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [CondicaoTempoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CondicaoTempoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CondicaoTempoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CondicaoTempoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CondicaoTempo(123);
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
        const entity = new CondicaoTempo();
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

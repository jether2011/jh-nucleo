import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { ConsolidacaoUpdateComponent } from 'app/entities/consolidacao/consolidacao-update.component';
import { ConsolidacaoService } from 'app/entities/consolidacao/consolidacao.service';
import { Consolidacao } from 'app/shared/model/consolidacao.model';

describe('Component Tests', () => {
  describe('Consolidacao Management Update Component', () => {
    let comp: ConsolidacaoUpdateComponent;
    let fixture: ComponentFixture<ConsolidacaoUpdateComponent>;
    let service: ConsolidacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [ConsolidacaoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConsolidacaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConsolidacaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConsolidacaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Consolidacao(123);
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
        const entity = new Consolidacao();
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

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { DiaSemanaUpdateComponent } from 'app/entities/dia-semana/dia-semana-update.component';
import { DiaSemanaService } from 'app/entities/dia-semana/dia-semana.service';
import { DiaSemana } from 'app/shared/model/dia-semana.model';

describe('Component Tests', () => {
  describe('DiaSemana Management Update Component', () => {
    let comp: DiaSemanaUpdateComponent;
    let fixture: ComponentFixture<DiaSemanaUpdateComponent>;
    let service: DiaSemanaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [DiaSemanaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DiaSemanaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DiaSemanaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DiaSemanaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DiaSemana(123);
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
        const entity = new DiaSemana();
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

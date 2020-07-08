import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { JornadaTrabalhoUpdateComponent } from 'app/entities/jornada-trabalho/jornada-trabalho-update.component';
import { JornadaTrabalhoService } from 'app/entities/jornada-trabalho/jornada-trabalho.service';
import { JornadaTrabalho } from 'app/shared/model/jornada-trabalho.model';

describe('Component Tests', () => {
  describe('JornadaTrabalho Management Update Component', () => {
    let comp: JornadaTrabalhoUpdateComponent;
    let fixture: ComponentFixture<JornadaTrabalhoUpdateComponent>;
    let service: JornadaTrabalhoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [JornadaTrabalhoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(JornadaTrabalhoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JornadaTrabalhoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JornadaTrabalhoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new JornadaTrabalho(123);
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
        const entity = new JornadaTrabalho();
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

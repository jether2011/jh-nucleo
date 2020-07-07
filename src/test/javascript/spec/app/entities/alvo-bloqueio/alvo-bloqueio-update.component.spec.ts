import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AlvoBloqueioUpdateComponent } from 'app/entities/alvo-bloqueio/alvo-bloqueio-update.component';
import { AlvoBloqueioService } from 'app/entities/alvo-bloqueio/alvo-bloqueio.service';
import { AlvoBloqueio } from 'app/shared/model/alvo-bloqueio.model';

describe('Component Tests', () => {
  describe('AlvoBloqueio Management Update Component', () => {
    let comp: AlvoBloqueioUpdateComponent;
    let fixture: ComponentFixture<AlvoBloqueioUpdateComponent>;
    let service: AlvoBloqueioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AlvoBloqueioUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AlvoBloqueioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlvoBloqueioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlvoBloqueioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AlvoBloqueio(123);
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
        const entity = new AlvoBloqueio();
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

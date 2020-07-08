import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { PlanoUsuarioUpdateComponent } from 'app/entities/plano-usuario/plano-usuario-update.component';
import { PlanoUsuarioService } from 'app/entities/plano-usuario/plano-usuario.service';
import { PlanoUsuario } from 'app/shared/model/plano-usuario.model';

describe('Component Tests', () => {
  describe('PlanoUsuario Management Update Component', () => {
    let comp: PlanoUsuarioUpdateComponent;
    let fixture: ComponentFixture<PlanoUsuarioUpdateComponent>;
    let service: PlanoUsuarioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [PlanoUsuarioUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PlanoUsuarioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlanoUsuarioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlanoUsuarioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlanoUsuario(123);
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
        const entity = new PlanoUsuario();
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

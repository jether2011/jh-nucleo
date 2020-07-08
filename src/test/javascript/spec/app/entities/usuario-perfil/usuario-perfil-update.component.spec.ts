import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { UsuarioPerfilUpdateComponent } from 'app/entities/usuario-perfil/usuario-perfil-update.component';
import { UsuarioPerfilService } from 'app/entities/usuario-perfil/usuario-perfil.service';
import { UsuarioPerfil } from 'app/shared/model/usuario-perfil.model';

describe('Component Tests', () => {
  describe('UsuarioPerfil Management Update Component', () => {
    let comp: UsuarioPerfilUpdateComponent;
    let fixture: ComponentFixture<UsuarioPerfilUpdateComponent>;
    let service: UsuarioPerfilService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [UsuarioPerfilUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UsuarioPerfilUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UsuarioPerfilUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UsuarioPerfilService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UsuarioPerfil(123);
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
        const entity = new UsuarioPerfil();
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

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { UsuarioPerfilDetailComponent } from 'app/entities/usuario-perfil/usuario-perfil-detail.component';
import { UsuarioPerfil } from 'app/shared/model/usuario-perfil.model';

describe('Component Tests', () => {
  describe('UsuarioPerfil Management Detail Component', () => {
    let comp: UsuarioPerfilDetailComponent;
    let fixture: ComponentFixture<UsuarioPerfilDetailComponent>;
    const route = ({ data: of({ usuarioPerfil: new UsuarioPerfil(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [UsuarioPerfilDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UsuarioPerfilDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UsuarioPerfilDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load usuarioPerfil on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.usuarioPerfil).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

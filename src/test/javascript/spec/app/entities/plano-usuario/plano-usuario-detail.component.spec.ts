import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { PlanoUsuarioDetailComponent } from 'app/entities/plano-usuario/plano-usuario-detail.component';
import { PlanoUsuario } from 'app/shared/model/plano-usuario.model';

describe('Component Tests', () => {
  describe('PlanoUsuario Management Detail Component', () => {
    let comp: PlanoUsuarioDetailComponent;
    let fixture: ComponentFixture<PlanoUsuarioDetailComponent>;
    const route = ({ data: of({ planoUsuario: new PlanoUsuario(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [PlanoUsuarioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PlanoUsuarioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlanoUsuarioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load planoUsuario on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.planoUsuario).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

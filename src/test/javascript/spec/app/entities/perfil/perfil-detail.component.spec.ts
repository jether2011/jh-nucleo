import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { PerfilDetailComponent } from 'app/entities/perfil/perfil-detail.component';
import { Perfil } from 'app/shared/model/perfil.model';

describe('Component Tests', () => {
  describe('Perfil Management Detail Component', () => {
    let comp: PerfilDetailComponent;
    let fixture: ComponentFixture<PerfilDetailComponent>;
    const route = ({ data: of({ perfil: new Perfil(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [PerfilDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PerfilDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PerfilDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load perfil on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.perfil).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

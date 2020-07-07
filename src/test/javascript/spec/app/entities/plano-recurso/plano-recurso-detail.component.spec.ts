import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { PlanoRecursoDetailComponent } from 'app/entities/plano-recurso/plano-recurso-detail.component';
import { PlanoRecurso } from 'app/shared/model/plano-recurso.model';

describe('Component Tests', () => {
  describe('PlanoRecurso Management Detail Component', () => {
    let comp: PlanoRecursoDetailComponent;
    let fixture: ComponentFixture<PlanoRecursoDetailComponent>;
    const route = ({ data: of({ planoRecurso: new PlanoRecurso(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [PlanoRecursoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PlanoRecursoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlanoRecursoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load planoRecurso on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.planoRecurso).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { ContatoPlanoRecursoDetailComponent } from 'app/entities/contato-plano-recurso/contato-plano-recurso-detail.component';
import { ContatoPlanoRecurso } from 'app/shared/model/contato-plano-recurso.model';

describe('Component Tests', () => {
  describe('ContatoPlanoRecurso Management Detail Component', () => {
    let comp: ContatoPlanoRecursoDetailComponent;
    let fixture: ComponentFixture<ContatoPlanoRecursoDetailComponent>;
    const route = ({ data: of({ contatoPlanoRecurso: new ContatoPlanoRecurso(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [ContatoPlanoRecursoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ContatoPlanoRecursoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContatoPlanoRecursoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contatoPlanoRecurso on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contatoPlanoRecurso).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

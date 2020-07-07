import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { RecursoTipoDetailComponent } from 'app/entities/recurso-tipo/recurso-tipo-detail.component';
import { RecursoTipo } from 'app/shared/model/recurso-tipo.model';

describe('Component Tests', () => {
  describe('RecursoTipo Management Detail Component', () => {
    let comp: RecursoTipoDetailComponent;
    let fixture: ComponentFixture<RecursoTipoDetailComponent>;
    const route = ({ data: of({ recursoTipo: new RecursoTipo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [RecursoTipoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RecursoTipoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecursoTipoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load recursoTipo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.recursoTipo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

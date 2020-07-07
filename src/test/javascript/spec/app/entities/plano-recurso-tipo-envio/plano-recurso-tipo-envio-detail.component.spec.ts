import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { PlanoRecursoTipoEnvioDetailComponent } from 'app/entities/plano-recurso-tipo-envio/plano-recurso-tipo-envio-detail.component';
import { PlanoRecursoTipoEnvio } from 'app/shared/model/plano-recurso-tipo-envio.model';

describe('Component Tests', () => {
  describe('PlanoRecursoTipoEnvio Management Detail Component', () => {
    let comp: PlanoRecursoTipoEnvioDetailComponent;
    let fixture: ComponentFixture<PlanoRecursoTipoEnvioDetailComponent>;
    const route = ({ data: of({ planoRecursoTipoEnvio: new PlanoRecursoTipoEnvio(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [PlanoRecursoTipoEnvioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PlanoRecursoTipoEnvioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlanoRecursoTipoEnvioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load planoRecursoTipoEnvio on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.planoRecursoTipoEnvio).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

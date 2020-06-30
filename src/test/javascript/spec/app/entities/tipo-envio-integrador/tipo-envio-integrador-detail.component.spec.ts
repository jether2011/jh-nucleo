import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { TipoEnvioIntegradorDetailComponent } from 'app/entities/tipo-envio-integrador/tipo-envio-integrador-detail.component';
import { TipoEnvioIntegrador } from 'app/shared/model/tipo-envio-integrador.model';

describe('Component Tests', () => {
  describe('TipoEnvioIntegrador Management Detail Component', () => {
    let comp: TipoEnvioIntegradorDetailComponent;
    let fixture: ComponentFixture<TipoEnvioIntegradorDetailComponent>;
    const route = ({ data: of({ tipoEnvioIntegrador: new TipoEnvioIntegrador(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [TipoEnvioIntegradorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TipoEnvioIntegradorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoEnvioIntegradorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoEnvioIntegrador on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoEnvioIntegrador).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { TipoEnvioDetailComponent } from 'app/entities/tipo-envio/tipo-envio-detail.component';
import { TipoEnvio } from 'app/shared/model/tipo-envio.model';

describe('Component Tests', () => {
  describe('TipoEnvio Management Detail Component', () => {
    let comp: TipoEnvioDetailComponent;
    let fixture: ComponentFixture<TipoEnvioDetailComponent>;
    const route = ({ data: of({ tipoEnvio: new TipoEnvio(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [TipoEnvioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TipoEnvioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoEnvioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoEnvio on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoEnvio).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

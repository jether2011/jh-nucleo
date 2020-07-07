import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { ContatoTipoEnvioDetailComponent } from 'app/entities/contato-tipo-envio/contato-tipo-envio-detail.component';
import { ContatoTipoEnvio } from 'app/shared/model/contato-tipo-envio.model';

describe('Component Tests', () => {
  describe('ContatoTipoEnvio Management Detail Component', () => {
    let comp: ContatoTipoEnvioDetailComponent;
    let fixture: ComponentFixture<ContatoTipoEnvioDetailComponent>;
    const route = ({ data: of({ contatoTipoEnvio: new ContatoTipoEnvio(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [ContatoTipoEnvioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ContatoTipoEnvioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContatoTipoEnvioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contatoTipoEnvio on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contatoTipoEnvio).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

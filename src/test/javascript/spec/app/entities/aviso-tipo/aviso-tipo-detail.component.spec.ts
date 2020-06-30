import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AvisoTipoDetailComponent } from 'app/entities/aviso-tipo/aviso-tipo-detail.component';
import { AvisoTipo } from 'app/shared/model/aviso-tipo.model';

describe('Component Tests', () => {
  describe('AvisoTipo Management Detail Component', () => {
    let comp: AvisoTipoDetailComponent;
    let fixture: ComponentFixture<AvisoTipoDetailComponent>;
    const route = ({ data: of({ avisoTipo: new AvisoTipo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AvisoTipoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AvisoTipoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvisoTipoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load avisoTipo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.avisoTipo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

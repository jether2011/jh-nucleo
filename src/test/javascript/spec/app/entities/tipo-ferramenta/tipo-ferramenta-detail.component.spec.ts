import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { TipoFerramentaDetailComponent } from 'app/entities/tipo-ferramenta/tipo-ferramenta-detail.component';
import { TipoFerramenta } from 'app/shared/model/tipo-ferramenta.model';

describe('Component Tests', () => {
  describe('TipoFerramenta Management Detail Component', () => {
    let comp: TipoFerramentaDetailComponent;
    let fixture: ComponentFixture<TipoFerramentaDetailComponent>;
    const route = ({ data: of({ tipoFerramenta: new TipoFerramenta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [TipoFerramentaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TipoFerramentaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoFerramentaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoFerramenta on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoFerramenta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

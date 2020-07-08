import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AcumuladoChuvaFaixaDetailComponent } from 'app/entities/acumulado-chuva-faixa/acumulado-chuva-faixa-detail.component';
import { AcumuladoChuvaFaixa } from 'app/shared/model/acumulado-chuva-faixa.model';

describe('Component Tests', () => {
  describe('AcumuladoChuvaFaixa Management Detail Component', () => {
    let comp: AcumuladoChuvaFaixaDetailComponent;
    let fixture: ComponentFixture<AcumuladoChuvaFaixaDetailComponent>;
    const route = ({ data: of({ acumuladoChuvaFaixa: new AcumuladoChuvaFaixa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AcumuladoChuvaFaixaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AcumuladoChuvaFaixaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AcumuladoChuvaFaixaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load acumuladoChuvaFaixa on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.acumuladoChuvaFaixa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

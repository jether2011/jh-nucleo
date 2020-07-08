import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { VariavelMeteorologicaDetailComponent } from 'app/entities/variavel-meteorologica/variavel-meteorologica-detail.component';
import { VariavelMeteorologica } from 'app/shared/model/variavel-meteorologica.model';

describe('Component Tests', () => {
  describe('VariavelMeteorologica Management Detail Component', () => {
    let comp: VariavelMeteorologicaDetailComponent;
    let fixture: ComponentFixture<VariavelMeteorologicaDetailComponent>;
    const route = ({ data: of({ variavelMeteorologica: new VariavelMeteorologica(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [VariavelMeteorologicaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(VariavelMeteorologicaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VariavelMeteorologicaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load variavelMeteorologica on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.variavelMeteorologica).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

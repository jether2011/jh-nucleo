import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AlertaFerramentaDetailComponent } from 'app/entities/alerta-ferramenta/alerta-ferramenta-detail.component';
import { AlertaFerramenta } from 'app/shared/model/alerta-ferramenta.model';

describe('Component Tests', () => {
  describe('AlertaFerramenta Management Detail Component', () => {
    let comp: AlertaFerramentaDetailComponent;
    let fixture: ComponentFixture<AlertaFerramentaDetailComponent>;
    const route = ({ data: of({ alertaFerramenta: new AlertaFerramenta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AlertaFerramentaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AlertaFerramentaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlertaFerramentaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load alertaFerramenta on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.alertaFerramenta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

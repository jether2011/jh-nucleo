import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AlertaRiscoDetailComponent } from 'app/entities/alerta-risco/alerta-risco-detail.component';
import { AlertaRisco } from 'app/shared/model/alerta-risco.model';

describe('Component Tests', () => {
  describe('AlertaRisco Management Detail Component', () => {
    let comp: AlertaRiscoDetailComponent;
    let fixture: ComponentFixture<AlertaRiscoDetailComponent>;
    const route = ({ data: of({ alertaRisco: new AlertaRisco(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AlertaRiscoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AlertaRiscoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlertaRiscoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load alertaRisco on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.alertaRisco).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

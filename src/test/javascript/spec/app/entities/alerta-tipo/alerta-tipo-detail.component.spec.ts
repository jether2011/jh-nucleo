import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AlertaTipoDetailComponent } from 'app/entities/alerta-tipo/alerta-tipo-detail.component';
import { AlertaTipo } from 'app/shared/model/alerta-tipo.model';

describe('Component Tests', () => {
  describe('AlertaTipo Management Detail Component', () => {
    let comp: AlertaTipoDetailComponent;
    let fixture: ComponentFixture<AlertaTipoDetailComponent>;
    const route = ({ data: of({ alertaTipo: new AlertaTipo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AlertaTipoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AlertaTipoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlertaTipoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load alertaTipo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.alertaTipo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AlertaDetailComponent } from 'app/entities/alerta/alerta-detail.component';
import { Alerta } from 'app/shared/model/alerta.model';

describe('Component Tests', () => {
  describe('Alerta Management Detail Component', () => {
    let comp: AlertaDetailComponent;
    let fixture: ComponentFixture<AlertaDetailComponent>;
    const route = ({ data: of({ alerta: new Alerta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AlertaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AlertaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlertaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load alerta on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.alerta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

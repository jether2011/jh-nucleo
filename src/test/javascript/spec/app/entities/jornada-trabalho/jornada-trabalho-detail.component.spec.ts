import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { JornadaTrabalhoDetailComponent } from 'app/entities/jornada-trabalho/jornada-trabalho-detail.component';
import { JornadaTrabalho } from 'app/shared/model/jornada-trabalho.model';

describe('Component Tests', () => {
  describe('JornadaTrabalho Management Detail Component', () => {
    let comp: JornadaTrabalhoDetailComponent;
    let fixture: ComponentFixture<JornadaTrabalhoDetailComponent>;
    const route = ({ data: of({ jornadaTrabalho: new JornadaTrabalho(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [JornadaTrabalhoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(JornadaTrabalhoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JornadaTrabalhoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load jornadaTrabalho on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jornadaTrabalho).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { PontosCardeaisDetailComponent } from 'app/entities/pontos-cardeais/pontos-cardeais-detail.component';
import { PontosCardeais } from 'app/shared/model/pontos-cardeais.model';

describe('Component Tests', () => {
  describe('PontosCardeais Management Detail Component', () => {
    let comp: PontosCardeaisDetailComponent;
    let fixture: ComponentFixture<PontosCardeaisDetailComponent>;
    const route = ({ data: of({ pontosCardeais: new PontosCardeais(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [PontosCardeaisDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PontosCardeaisDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PontosCardeaisDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pontosCardeais on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pontosCardeais).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

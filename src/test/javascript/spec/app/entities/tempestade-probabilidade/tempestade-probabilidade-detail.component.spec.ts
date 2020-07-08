import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { TempestadeProbabilidadeDetailComponent } from 'app/entities/tempestade-probabilidade/tempestade-probabilidade-detail.component';
import { TempestadeProbabilidade } from 'app/shared/model/tempestade-probabilidade.model';

describe('Component Tests', () => {
  describe('TempestadeProbabilidade Management Detail Component', () => {
    let comp: TempestadeProbabilidadeDetailComponent;
    let fixture: ComponentFixture<TempestadeProbabilidadeDetailComponent>;
    const route = ({ data: of({ tempestadeProbabilidade: new TempestadeProbabilidade(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [TempestadeProbabilidadeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TempestadeProbabilidadeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TempestadeProbabilidadeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tempestadeProbabilidade on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tempestadeProbabilidade).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

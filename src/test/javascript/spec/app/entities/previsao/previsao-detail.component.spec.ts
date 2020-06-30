import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { PrevisaoDetailComponent } from 'app/entities/previsao/previsao-detail.component';
import { Previsao } from 'app/shared/model/previsao.model';

describe('Component Tests', () => {
  describe('Previsao Management Detail Component', () => {
    let comp: PrevisaoDetailComponent;
    let fixture: ComponentFixture<PrevisaoDetailComponent>;
    const route = ({ data: of({ previsao: new Previsao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [PrevisaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PrevisaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrevisaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load previsao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.previsao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

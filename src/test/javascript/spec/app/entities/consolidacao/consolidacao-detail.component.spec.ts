import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { ConsolidacaoDetailComponent } from 'app/entities/consolidacao/consolidacao-detail.component';
import { Consolidacao } from 'app/shared/model/consolidacao.model';

describe('Component Tests', () => {
  describe('Consolidacao Management Detail Component', () => {
    let comp: ConsolidacaoDetailComponent;
    let fixture: ComponentFixture<ConsolidacaoDetailComponent>;
    const route = ({ data: of({ consolidacao: new Consolidacao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [ConsolidacaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConsolidacaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConsolidacaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load consolidacao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.consolidacao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

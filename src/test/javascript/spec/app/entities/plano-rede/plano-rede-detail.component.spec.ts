import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { PlanoRedeDetailComponent } from 'app/entities/plano-rede/plano-rede-detail.component';
import { PlanoRede } from 'app/shared/model/plano-rede.model';

describe('Component Tests', () => {
  describe('PlanoRede Management Detail Component', () => {
    let comp: PlanoRedeDetailComponent;
    let fixture: ComponentFixture<PlanoRedeDetailComponent>;
    const route = ({ data: of({ planoRede: new PlanoRede(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [PlanoRedeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PlanoRedeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlanoRedeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load planoRede on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.planoRede).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

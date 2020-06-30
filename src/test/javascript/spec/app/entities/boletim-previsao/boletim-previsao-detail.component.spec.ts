import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { BoletimPrevisaoDetailComponent } from 'app/entities/boletim-previsao/boletim-previsao-detail.component';
import { BoletimPrevisao } from 'app/shared/model/boletim-previsao.model';

describe('Component Tests', () => {
  describe('BoletimPrevisao Management Detail Component', () => {
    let comp: BoletimPrevisaoDetailComponent;
    let fixture: ComponentFixture<BoletimPrevisaoDetailComponent>;
    const route = ({ data: of({ boletimPrevisao: new BoletimPrevisao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [BoletimPrevisaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BoletimPrevisaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BoletimPrevisaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load boletimPrevisao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.boletimPrevisao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

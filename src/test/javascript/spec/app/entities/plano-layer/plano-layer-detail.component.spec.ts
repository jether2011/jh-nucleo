import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { PlanoLayerDetailComponent } from 'app/entities/plano-layer/plano-layer-detail.component';
import { PlanoLayer } from 'app/shared/model/plano-layer.model';

describe('Component Tests', () => {
  describe('PlanoLayer Management Detail Component', () => {
    let comp: PlanoLayerDetailComponent;
    let fixture: ComponentFixture<PlanoLayerDetailComponent>;
    const route = ({ data: of({ planoLayer: new PlanoLayer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [PlanoLayerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PlanoLayerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlanoLayerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load planoLayer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.planoLayer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { PlanoDetailComponent } from 'app/entities/plano/plano-detail.component';
import { Plano } from 'app/shared/model/plano.model';

describe('Component Tests', () => {
  describe('Plano Management Detail Component', () => {
    let comp: PlanoDetailComponent;
    let fixture: ComponentFixture<PlanoDetailComponent>;
    const route = ({ data: of({ plano: new Plano(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [PlanoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PlanoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlanoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load plano on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.plano).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

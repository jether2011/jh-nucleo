import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { BoletimPrevObsDetailComponent } from 'app/entities/boletim-prev-obs/boletim-prev-obs-detail.component';
import { BoletimPrevObs } from 'app/shared/model/boletim-prev-obs.model';

describe('Component Tests', () => {
  describe('BoletimPrevObs Management Detail Component', () => {
    let comp: BoletimPrevObsDetailComponent;
    let fixture: ComponentFixture<BoletimPrevObsDetailComponent>;
    const route = ({ data: of({ boletimPrevObs: new BoletimPrevObs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [BoletimPrevObsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BoletimPrevObsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BoletimPrevObsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load boletimPrevObs on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.boletimPrevObs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { BoletimDetailComponent } from 'app/entities/boletim/boletim-detail.component';
import { Boletim } from 'app/shared/model/boletim.model';

describe('Component Tests', () => {
  describe('Boletim Management Detail Component', () => {
    let comp: BoletimDetailComponent;
    let fixture: ComponentFixture<BoletimDetailComponent>;
    const route = ({ data: of({ boletim: new Boletim(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [BoletimDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BoletimDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BoletimDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load boletim on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.boletim).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

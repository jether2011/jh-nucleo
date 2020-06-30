import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { BoletimPrevVariavelMetDetailComponent } from 'app/entities/boletim-prev-variavel-met/boletim-prev-variavel-met-detail.component';
import { BoletimPrevVariavelMet } from 'app/shared/model/boletim-prev-variavel-met.model';

describe('Component Tests', () => {
  describe('BoletimPrevVariavelMet Management Detail Component', () => {
    let comp: BoletimPrevVariavelMetDetailComponent;
    let fixture: ComponentFixture<BoletimPrevVariavelMetDetailComponent>;
    const route = ({ data: of({ boletimPrevVariavelMet: new BoletimPrevVariavelMet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [BoletimPrevVariavelMetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BoletimPrevVariavelMetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BoletimPrevVariavelMetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load boletimPrevVariavelMet on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.boletimPrevVariavelMet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

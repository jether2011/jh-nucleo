import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { FerramentaDetailComponent } from 'app/entities/ferramenta/ferramenta-detail.component';
import { Ferramenta } from 'app/shared/model/ferramenta.model';

describe('Component Tests', () => {
  describe('Ferramenta Management Detail Component', () => {
    let comp: FerramentaDetailComponent;
    let fixture: ComponentFixture<FerramentaDetailComponent>;
    const route = ({ data: of({ ferramenta: new Ferramenta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [FerramentaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FerramentaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FerramentaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ferramenta on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ferramenta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

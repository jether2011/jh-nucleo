import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { MeteogramaDetailComponent } from 'app/entities/meteograma/meteograma-detail.component';
import { Meteograma } from 'app/shared/model/meteograma.model';

describe('Component Tests', () => {
  describe('Meteograma Management Detail Component', () => {
    let comp: MeteogramaDetailComponent;
    let fixture: ComponentFixture<MeteogramaDetailComponent>;
    const route = ({ data: of({ meteograma: new Meteograma(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [MeteogramaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MeteogramaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MeteogramaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load meteograma on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.meteograma).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

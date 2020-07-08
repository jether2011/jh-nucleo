import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { IntensidadeChuvaDetailComponent } from 'app/entities/intensidade-chuva/intensidade-chuva-detail.component';
import { IntensidadeChuva } from 'app/shared/model/intensidade-chuva.model';

describe('Component Tests', () => {
  describe('IntensidadeChuva Management Detail Component', () => {
    let comp: IntensidadeChuvaDetailComponent;
    let fixture: ComponentFixture<IntensidadeChuvaDetailComponent>;
    const route = ({ data: of({ intensidadeChuva: new IntensidadeChuva(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [IntensidadeChuvaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IntensidadeChuvaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IntensidadeChuvaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load intensidadeChuva on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.intensidadeChuva).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { TempestadeNivelDetailComponent } from 'app/entities/tempestade-nivel/tempestade-nivel-detail.component';
import { TempestadeNivel } from 'app/shared/model/tempestade-nivel.model';

describe('Component Tests', () => {
  describe('TempestadeNivel Management Detail Component', () => {
    let comp: TempestadeNivelDetailComponent;
    let fixture: ComponentFixture<TempestadeNivelDetailComponent>;
    const route = ({ data: of({ tempestadeNivel: new TempestadeNivel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [TempestadeNivelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TempestadeNivelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TempestadeNivelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tempestadeNivel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tempestadeNivel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

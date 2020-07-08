import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { DescargaDetailComponent } from 'app/entities/descarga/descarga-detail.component';
import { Descarga } from 'app/shared/model/descarga.model';

describe('Component Tests', () => {
  describe('Descarga Management Detail Component', () => {
    let comp: DescargaDetailComponent;
    let fixture: ComponentFixture<DescargaDetailComponent>;
    const route = ({ data: of({ descarga: new Descarga(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [DescargaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DescargaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DescargaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load descarga on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.descarga).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { DescargaUnidadeDetailComponent } from 'app/entities/descarga-unidade/descarga-unidade-detail.component';
import { DescargaUnidade } from 'app/shared/model/descarga-unidade.model';

describe('Component Tests', () => {
  describe('DescargaUnidade Management Detail Component', () => {
    let comp: DescargaUnidadeDetailComponent;
    let fixture: ComponentFixture<DescargaUnidadeDetailComponent>;
    const route = ({ data: of({ descargaUnidade: new DescargaUnidade(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [DescargaUnidadeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DescargaUnidadeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DescargaUnidadeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load descargaUnidade on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.descargaUnidade).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

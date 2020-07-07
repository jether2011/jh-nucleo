import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { DescargaTipoDetailComponent } from 'app/entities/descarga-tipo/descarga-tipo-detail.component';
import { DescargaTipo } from 'app/shared/model/descarga-tipo.model';

describe('Component Tests', () => {
  describe('DescargaTipo Management Detail Component', () => {
    let comp: DescargaTipoDetailComponent;
    let fixture: ComponentFixture<DescargaTipoDetailComponent>;
    const route = ({ data: of({ descargaTipo: new DescargaTipo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [DescargaTipoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DescargaTipoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DescargaTipoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load descargaTipo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.descargaTipo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

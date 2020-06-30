import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AvisoMeteorologicoDetailComponent } from 'app/entities/aviso-meteorologico/aviso-meteorologico-detail.component';
import { AvisoMeteorologico } from 'app/shared/model/aviso-meteorologico.model';

describe('Component Tests', () => {
  describe('AvisoMeteorologico Management Detail Component', () => {
    let comp: AvisoMeteorologicoDetailComponent;
    let fixture: ComponentFixture<AvisoMeteorologicoDetailComponent>;
    const route = ({ data: of({ avisoMeteorologico: new AvisoMeteorologico(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AvisoMeteorologicoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AvisoMeteorologicoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvisoMeteorologicoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load avisoMeteorologico on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.avisoMeteorologico).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

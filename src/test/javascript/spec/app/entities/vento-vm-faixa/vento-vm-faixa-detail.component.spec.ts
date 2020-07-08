import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { VentoVmFaixaDetailComponent } from 'app/entities/vento-vm-faixa/vento-vm-faixa-detail.component';
import { VentoVmFaixa } from 'app/shared/model/vento-vm-faixa.model';

describe('Component Tests', () => {
  describe('VentoVmFaixa Management Detail Component', () => {
    let comp: VentoVmFaixaDetailComponent;
    let fixture: ComponentFixture<VentoVmFaixaDetailComponent>;
    const route = ({ data: of({ ventoVmFaixa: new VentoVmFaixa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [VentoVmFaixaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(VentoVmFaixaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VentoVmFaixaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ventoVmFaixa on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ventoVmFaixa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

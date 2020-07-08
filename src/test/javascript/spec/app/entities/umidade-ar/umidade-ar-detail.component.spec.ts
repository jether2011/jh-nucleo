import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { UmidadeArDetailComponent } from 'app/entities/umidade-ar/umidade-ar-detail.component';
import { UmidadeAr } from 'app/shared/model/umidade-ar.model';

describe('Component Tests', () => {
  describe('UmidadeAr Management Detail Component', () => {
    let comp: UmidadeArDetailComponent;
    let fixture: ComponentFixture<UmidadeArDetailComponent>;
    const route = ({ data: of({ umidadeAr: new UmidadeAr(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [UmidadeArDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UmidadeArDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UmidadeArDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load umidadeAr on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.umidadeAr).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

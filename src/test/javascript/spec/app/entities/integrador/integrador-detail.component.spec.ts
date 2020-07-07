import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { IntegradorDetailComponent } from 'app/entities/integrador/integrador-detail.component';
import { Integrador } from 'app/shared/model/integrador.model';

describe('Component Tests', () => {
  describe('Integrador Management Detail Component', () => {
    let comp: IntegradorDetailComponent;
    let fixture: ComponentFixture<IntegradorDetailComponent>;
    const route = ({ data: of({ integrador: new Integrador(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [IntegradorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IntegradorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IntegradorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load integrador on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.integrador).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

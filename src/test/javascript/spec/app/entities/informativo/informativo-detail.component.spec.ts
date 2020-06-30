import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { InformativoDetailComponent } from 'app/entities/informativo/informativo-detail.component';
import { Informativo } from 'app/shared/model/informativo.model';

describe('Component Tests', () => {
  describe('Informativo Management Detail Component', () => {
    let comp: InformativoDetailComponent;
    let fixture: ComponentFixture<InformativoDetailComponent>;
    const route = ({ data: of({ informativo: new Informativo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [InformativoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InformativoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InformativoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load informativo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.informativo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

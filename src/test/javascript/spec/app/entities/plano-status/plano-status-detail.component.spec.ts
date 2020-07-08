import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { PlanoStatusDetailComponent } from 'app/entities/plano-status/plano-status-detail.component';
import { PlanoStatus } from 'app/shared/model/plano-status.model';

describe('Component Tests', () => {
  describe('PlanoStatus Management Detail Component', () => {
    let comp: PlanoStatusDetailComponent;
    let fixture: ComponentFixture<PlanoStatusDetailComponent>;
    const route = ({ data: of({ planoStatus: new PlanoStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [PlanoStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PlanoStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlanoStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load planoStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.planoStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

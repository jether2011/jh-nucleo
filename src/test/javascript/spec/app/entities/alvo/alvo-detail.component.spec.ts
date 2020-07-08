import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AlvoDetailComponent } from 'app/entities/alvo/alvo-detail.component';
import { Alvo } from 'app/shared/model/alvo.model';

describe('Component Tests', () => {
  describe('Alvo Management Detail Component', () => {
    let comp: AlvoDetailComponent;
    let fixture: ComponentFixture<AlvoDetailComponent>;
    const route = ({ data: of({ alvo: new Alvo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AlvoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AlvoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlvoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load alvo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.alvo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

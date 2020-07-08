import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AlvoBloqueioDetailComponent } from 'app/entities/alvo-bloqueio/alvo-bloqueio-detail.component';
import { AlvoBloqueio } from 'app/shared/model/alvo-bloqueio.model';

describe('Component Tests', () => {
  describe('AlvoBloqueio Management Detail Component', () => {
    let comp: AlvoBloqueioDetailComponent;
    let fixture: ComponentFixture<AlvoBloqueioDetailComponent>;
    const route = ({ data: of({ alvoBloqueio: new AlvoBloqueio(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AlvoBloqueioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AlvoBloqueioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlvoBloqueioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load alvoBloqueio on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.alvoBloqueio).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

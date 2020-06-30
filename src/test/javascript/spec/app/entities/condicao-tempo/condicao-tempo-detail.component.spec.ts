import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { CondicaoTempoDetailComponent } from 'app/entities/condicao-tempo/condicao-tempo-detail.component';
import { CondicaoTempo } from 'app/shared/model/condicao-tempo.model';

describe('Component Tests', () => {
  describe('CondicaoTempo Management Detail Component', () => {
    let comp: CondicaoTempoDetailComponent;
    let fixture: ComponentFixture<CondicaoTempoDetailComponent>;
    const route = ({ data: of({ condicaoTempo: new CondicaoTempo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [CondicaoTempoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CondicaoTempoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CondicaoTempoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load condicaoTempo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.condicaoTempo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

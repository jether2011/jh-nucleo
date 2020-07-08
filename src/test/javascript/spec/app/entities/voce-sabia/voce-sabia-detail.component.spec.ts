import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { VoceSabiaDetailComponent } from 'app/entities/voce-sabia/voce-sabia-detail.component';
import { VoceSabia } from 'app/shared/model/voce-sabia.model';

describe('Component Tests', () => {
  describe('VoceSabia Management Detail Component', () => {
    let comp: VoceSabiaDetailComponent;
    let fixture: ComponentFixture<VoceSabiaDetailComponent>;
    const route = ({ data: of({ voceSabia: new VoceSabia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [VoceSabiaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(VoceSabiaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VoceSabiaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load voceSabia on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.voceSabia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

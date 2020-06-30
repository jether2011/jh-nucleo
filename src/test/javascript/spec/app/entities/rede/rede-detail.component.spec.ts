import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { RedeDetailComponent } from 'app/entities/rede/rede-detail.component';
import { Rede } from 'app/shared/model/rede.model';

describe('Component Tests', () => {
  describe('Rede Management Detail Component', () => {
    let comp: RedeDetailComponent;
    let fixture: ComponentFixture<RedeDetailComponent>;
    const route = ({ data: of({ rede: new Rede(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [RedeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RedeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RedeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rede on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rede).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

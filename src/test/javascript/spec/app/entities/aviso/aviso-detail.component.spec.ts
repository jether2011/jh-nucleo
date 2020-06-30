import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { AvisoDetailComponent } from 'app/entities/aviso/aviso-detail.component';
import { Aviso } from 'app/shared/model/aviso.model';

describe('Component Tests', () => {
  describe('Aviso Management Detail Component', () => {
    let comp: AvisoDetailComponent;
    let fixture: ComponentFixture<AvisoDetailComponent>;
    const route = ({ data: of({ aviso: new Aviso(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AvisoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AvisoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvisoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load aviso on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.aviso).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

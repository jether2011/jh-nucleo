import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { ContatoAlvoDetailComponent } from 'app/entities/contato-alvo/contato-alvo-detail.component';
import { ContatoAlvo } from 'app/shared/model/contato-alvo.model';

describe('Component Tests', () => {
  describe('ContatoAlvo Management Detail Component', () => {
    let comp: ContatoAlvoDetailComponent;
    let fixture: ComponentFixture<ContatoAlvoDetailComponent>;
    const route = ({ data: of({ contatoAlvo: new ContatoAlvo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [ContatoAlvoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ContatoAlvoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContatoAlvoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contatoAlvo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contatoAlvo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

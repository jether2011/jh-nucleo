import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NucleoTestModule } from '../../../test.module';
import { RecursoTemplateDetailComponent } from 'app/entities/recurso-template/recurso-template-detail.component';
import { RecursoTemplate } from 'app/shared/model/recurso-template.model';

describe('Component Tests', () => {
  describe('RecursoTemplate Management Detail Component', () => {
    let comp: RecursoTemplateDetailComponent;
    let fixture: ComponentFixture<RecursoTemplateDetailComponent>;
    const route = ({ data: of({ recursoTemplate: new RecursoTemplate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [RecursoTemplateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RecursoTemplateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecursoTemplateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load recursoTemplate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.recursoTemplate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

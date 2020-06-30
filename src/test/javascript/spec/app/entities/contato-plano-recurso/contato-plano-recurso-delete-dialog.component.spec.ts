import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NucleoTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { ContatoPlanoRecursoDeleteDialogComponent } from 'app/entities/contato-plano-recurso/contato-plano-recurso-delete-dialog.component';
import { ContatoPlanoRecursoService } from 'app/entities/contato-plano-recurso/contato-plano-recurso.service';

describe('Component Tests', () => {
  describe('ContatoPlanoRecurso Management Delete Component', () => {
    let comp: ContatoPlanoRecursoDeleteDialogComponent;
    let fixture: ComponentFixture<ContatoPlanoRecursoDeleteDialogComponent>;
    let service: ContatoPlanoRecursoService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [ContatoPlanoRecursoDeleteDialogComponent],
      })
        .overrideTemplate(ContatoPlanoRecursoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContatoPlanoRecursoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContatoPlanoRecursoService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});

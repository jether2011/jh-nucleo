import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NucleoTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { AlvoBloqueioDeleteDialogComponent } from 'app/entities/alvo-bloqueio/alvo-bloqueio-delete-dialog.component';
import { AlvoBloqueioService } from 'app/entities/alvo-bloqueio/alvo-bloqueio.service';

describe('Component Tests', () => {
  describe('AlvoBloqueio Management Delete Component', () => {
    let comp: AlvoBloqueioDeleteDialogComponent;
    let fixture: ComponentFixture<AlvoBloqueioDeleteDialogComponent>;
    let service: AlvoBloqueioService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [AlvoBloqueioDeleteDialogComponent],
      })
        .overrideTemplate(AlvoBloqueioDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlvoBloqueioDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlvoBloqueioService);
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

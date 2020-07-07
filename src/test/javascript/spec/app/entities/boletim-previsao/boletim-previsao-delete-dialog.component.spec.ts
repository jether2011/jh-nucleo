import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NucleoTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { BoletimPrevisaoDeleteDialogComponent } from 'app/entities/boletim-previsao/boletim-previsao-delete-dialog.component';
import { BoletimPrevisaoService } from 'app/entities/boletim-previsao/boletim-previsao.service';

describe('Component Tests', () => {
  describe('BoletimPrevisao Management Delete Component', () => {
    let comp: BoletimPrevisaoDeleteDialogComponent;
    let fixture: ComponentFixture<BoletimPrevisaoDeleteDialogComponent>;
    let service: BoletimPrevisaoService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [BoletimPrevisaoDeleteDialogComponent],
      })
        .overrideTemplate(BoletimPrevisaoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BoletimPrevisaoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BoletimPrevisaoService);
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

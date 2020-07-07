import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NucleoTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { TipoEnvioIntegradorDeleteDialogComponent } from 'app/entities/tipo-envio-integrador/tipo-envio-integrador-delete-dialog.component';
import { TipoEnvioIntegradorService } from 'app/entities/tipo-envio-integrador/tipo-envio-integrador.service';

describe('Component Tests', () => {
  describe('TipoEnvioIntegrador Management Delete Component', () => {
    let comp: TipoEnvioIntegradorDeleteDialogComponent;
    let fixture: ComponentFixture<TipoEnvioIntegradorDeleteDialogComponent>;
    let service: TipoEnvioIntegradorService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NucleoTestModule],
        declarations: [TipoEnvioIntegradorDeleteDialogComponent],
      })
        .overrideTemplate(TipoEnvioIntegradorDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoEnvioIntegradorDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoEnvioIntegradorService);
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

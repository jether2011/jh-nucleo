import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { AlertaTipoComponent } from './alerta-tipo.component';
import { AlertaTipoDetailComponent } from './alerta-tipo-detail.component';
import { AlertaTipoUpdateComponent } from './alerta-tipo-update.component';
import { AlertaTipoDeleteDialogComponent } from './alerta-tipo-delete-dialog.component';
import { alertaTipoRoute } from './alerta-tipo.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(alertaTipoRoute)],
  declarations: [AlertaTipoComponent, AlertaTipoDetailComponent, AlertaTipoUpdateComponent, AlertaTipoDeleteDialogComponent],
  entryComponents: [AlertaTipoDeleteDialogComponent],
})
export class NucleoAlertaTipoModule {}

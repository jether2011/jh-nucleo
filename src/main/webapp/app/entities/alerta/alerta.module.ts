import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { AlertaComponent } from './alerta.component';
import { AlertaDetailComponent } from './alerta-detail.component';
import { AlertaUpdateComponent } from './alerta-update.component';
import { AlertaDeleteDialogComponent } from './alerta-delete-dialog.component';
import { alertaRoute } from './alerta.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(alertaRoute)],
  declarations: [AlertaComponent, AlertaDetailComponent, AlertaUpdateComponent, AlertaDeleteDialogComponent],
  entryComponents: [AlertaDeleteDialogComponent],
})
export class NucleoAlertaModule {}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { AlertaRiscoComponent } from './alerta-risco.component';
import { AlertaRiscoDetailComponent } from './alerta-risco-detail.component';
import { AlertaRiscoUpdateComponent } from './alerta-risco-update.component';
import { AlertaRiscoDeleteDialogComponent } from './alerta-risco-delete-dialog.component';
import { alertaRiscoRoute } from './alerta-risco.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(alertaRiscoRoute)],
  declarations: [AlertaRiscoComponent, AlertaRiscoDetailComponent, AlertaRiscoUpdateComponent, AlertaRiscoDeleteDialogComponent],
  entryComponents: [AlertaRiscoDeleteDialogComponent],
})
export class NucleoAlertaRiscoModule {}

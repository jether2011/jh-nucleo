import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { PlanoStatusComponent } from './plano-status.component';
import { PlanoStatusDetailComponent } from './plano-status-detail.component';
import { PlanoStatusUpdateComponent } from './plano-status-update.component';
import { PlanoStatusDeleteDialogComponent } from './plano-status-delete-dialog.component';
import { planoStatusRoute } from './plano-status.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(planoStatusRoute)],
  declarations: [PlanoStatusComponent, PlanoStatusDetailComponent, PlanoStatusUpdateComponent, PlanoStatusDeleteDialogComponent],
  entryComponents: [PlanoStatusDeleteDialogComponent],
})
export class NucleoPlanoStatusModule {}

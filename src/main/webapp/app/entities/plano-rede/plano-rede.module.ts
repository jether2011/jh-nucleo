import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { PlanoRedeComponent } from './plano-rede.component';
import { PlanoRedeDetailComponent } from './plano-rede-detail.component';
import { PlanoRedeUpdateComponent } from './plano-rede-update.component';
import { PlanoRedeDeleteDialogComponent } from './plano-rede-delete-dialog.component';
import { planoRedeRoute } from './plano-rede.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(planoRedeRoute)],
  declarations: [PlanoRedeComponent, PlanoRedeDetailComponent, PlanoRedeUpdateComponent, PlanoRedeDeleteDialogComponent],
  entryComponents: [PlanoRedeDeleteDialogComponent],
})
export class NucleoPlanoRedeModule {}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { RedeComponent } from './rede.component';
import { RedeDetailComponent } from './rede-detail.component';
import { RedeUpdateComponent } from './rede-update.component';
import { RedeDeleteDialogComponent } from './rede-delete-dialog.component';
import { redeRoute } from './rede.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(redeRoute)],
  declarations: [RedeComponent, RedeDetailComponent, RedeUpdateComponent, RedeDeleteDialogComponent],
  entryComponents: [RedeDeleteDialogComponent],
})
export class NucleoRedeModule {}

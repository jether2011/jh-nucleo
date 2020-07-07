import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { VentoVmFaixaComponent } from './vento-vm-faixa.component';
import { VentoVmFaixaDetailComponent } from './vento-vm-faixa-detail.component';
import { VentoVmFaixaUpdateComponent } from './vento-vm-faixa-update.component';
import { VentoVmFaixaDeleteDialogComponent } from './vento-vm-faixa-delete-dialog.component';
import { ventoVmFaixaRoute } from './vento-vm-faixa.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(ventoVmFaixaRoute)],
  declarations: [VentoVmFaixaComponent, VentoVmFaixaDetailComponent, VentoVmFaixaUpdateComponent, VentoVmFaixaDeleteDialogComponent],
  entryComponents: [VentoVmFaixaDeleteDialogComponent],
})
export class NucleoVentoVmFaixaModule {}

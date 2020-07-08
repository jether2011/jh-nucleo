import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { PlanoLayerComponent } from './plano-layer.component';
import { PlanoLayerDetailComponent } from './plano-layer-detail.component';
import { PlanoLayerUpdateComponent } from './plano-layer-update.component';
import { PlanoLayerDeleteDialogComponent } from './plano-layer-delete-dialog.component';
import { planoLayerRoute } from './plano-layer.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(planoLayerRoute)],
  declarations: [PlanoLayerComponent, PlanoLayerDetailComponent, PlanoLayerUpdateComponent, PlanoLayerDeleteDialogComponent],
  entryComponents: [PlanoLayerDeleteDialogComponent],
})
export class NucleoPlanoLayerModule {}

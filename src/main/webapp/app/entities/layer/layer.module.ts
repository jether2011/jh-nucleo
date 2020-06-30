import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { LayerComponent } from './layer.component';
import { LayerDetailComponent } from './layer-detail.component';
import { LayerUpdateComponent } from './layer-update.component';
import { LayerDeleteDialogComponent } from './layer-delete-dialog.component';
import { layerRoute } from './layer.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(layerRoute)],
  declarations: [LayerComponent, LayerDetailComponent, LayerUpdateComponent, LayerDeleteDialogComponent],
  entryComponents: [LayerDeleteDialogComponent],
})
export class NucleoLayerModule {}

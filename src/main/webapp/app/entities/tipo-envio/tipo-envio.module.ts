import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { TipoEnvioComponent } from './tipo-envio.component';
import { TipoEnvioDetailComponent } from './tipo-envio-detail.component';
import { TipoEnvioUpdateComponent } from './tipo-envio-update.component';
import { TipoEnvioDeleteDialogComponent } from './tipo-envio-delete-dialog.component';
import { tipoEnvioRoute } from './tipo-envio.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(tipoEnvioRoute)],
  declarations: [TipoEnvioComponent, TipoEnvioDetailComponent, TipoEnvioUpdateComponent, TipoEnvioDeleteDialogComponent],
  entryComponents: [TipoEnvioDeleteDialogComponent],
})
export class NucleoTipoEnvioModule {}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { TipoEnvioIntegradorComponent } from './tipo-envio-integrador.component';
import { TipoEnvioIntegradorDetailComponent } from './tipo-envio-integrador-detail.component';
import { TipoEnvioIntegradorUpdateComponent } from './tipo-envio-integrador-update.component';
import { TipoEnvioIntegradorDeleteDialogComponent } from './tipo-envio-integrador-delete-dialog.component';
import { tipoEnvioIntegradorRoute } from './tipo-envio-integrador.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(tipoEnvioIntegradorRoute)],
  declarations: [
    TipoEnvioIntegradorComponent,
    TipoEnvioIntegradorDetailComponent,
    TipoEnvioIntegradorUpdateComponent,
    TipoEnvioIntegradorDeleteDialogComponent,
  ],
  entryComponents: [TipoEnvioIntegradorDeleteDialogComponent],
})
export class NucleoTipoEnvioIntegradorModule {}

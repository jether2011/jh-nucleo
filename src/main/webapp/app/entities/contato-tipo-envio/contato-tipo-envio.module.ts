import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { ContatoTipoEnvioComponent } from './contato-tipo-envio.component';
import { ContatoTipoEnvioDetailComponent } from './contato-tipo-envio-detail.component';
import { ContatoTipoEnvioUpdateComponent } from './contato-tipo-envio-update.component';
import { ContatoTipoEnvioDeleteDialogComponent } from './contato-tipo-envio-delete-dialog.component';
import { contatoTipoEnvioRoute } from './contato-tipo-envio.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(contatoTipoEnvioRoute)],
  declarations: [
    ContatoTipoEnvioComponent,
    ContatoTipoEnvioDetailComponent,
    ContatoTipoEnvioUpdateComponent,
    ContatoTipoEnvioDeleteDialogComponent,
  ],
  entryComponents: [ContatoTipoEnvioDeleteDialogComponent],
})
export class NucleoContatoTipoEnvioModule {}

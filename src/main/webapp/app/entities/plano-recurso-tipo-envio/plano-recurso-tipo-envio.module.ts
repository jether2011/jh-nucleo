import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { PlanoRecursoTipoEnvioComponent } from './plano-recurso-tipo-envio.component';
import { PlanoRecursoTipoEnvioDetailComponent } from './plano-recurso-tipo-envio-detail.component';
import { PlanoRecursoTipoEnvioUpdateComponent } from './plano-recurso-tipo-envio-update.component';
import { PlanoRecursoTipoEnvioDeleteDialogComponent } from './plano-recurso-tipo-envio-delete-dialog.component';
import { planoRecursoTipoEnvioRoute } from './plano-recurso-tipo-envio.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(planoRecursoTipoEnvioRoute)],
  declarations: [
    PlanoRecursoTipoEnvioComponent,
    PlanoRecursoTipoEnvioDetailComponent,
    PlanoRecursoTipoEnvioUpdateComponent,
    PlanoRecursoTipoEnvioDeleteDialogComponent,
  ],
  entryComponents: [PlanoRecursoTipoEnvioDeleteDialogComponent],
})
export class NucleoPlanoRecursoTipoEnvioModule {}

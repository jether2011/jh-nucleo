import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { ContatoPlanoRecursoComponent } from './contato-plano-recurso.component';
import { ContatoPlanoRecursoDetailComponent } from './contato-plano-recurso-detail.component';
import { ContatoPlanoRecursoUpdateComponent } from './contato-plano-recurso-update.component';
import { ContatoPlanoRecursoDeleteDialogComponent } from './contato-plano-recurso-delete-dialog.component';
import { contatoPlanoRecursoRoute } from './contato-plano-recurso.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(contatoPlanoRecursoRoute)],
  declarations: [
    ContatoPlanoRecursoComponent,
    ContatoPlanoRecursoDetailComponent,
    ContatoPlanoRecursoUpdateComponent,
    ContatoPlanoRecursoDeleteDialogComponent,
  ],
  entryComponents: [ContatoPlanoRecursoDeleteDialogComponent],
})
export class NucleoContatoPlanoRecursoModule {}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { PlanoUsuarioComponent } from './plano-usuario.component';
import { PlanoUsuarioDetailComponent } from './plano-usuario-detail.component';
import { PlanoUsuarioUpdateComponent } from './plano-usuario-update.component';
import { PlanoUsuarioDeleteDialogComponent } from './plano-usuario-delete-dialog.component';
import { planoUsuarioRoute } from './plano-usuario.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(planoUsuarioRoute)],
  declarations: [PlanoUsuarioComponent, PlanoUsuarioDetailComponent, PlanoUsuarioUpdateComponent, PlanoUsuarioDeleteDialogComponent],
  entryComponents: [PlanoUsuarioDeleteDialogComponent],
})
export class NucleoPlanoUsuarioModule {}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { PlanoRecursoComponent } from './plano-recurso.component';
import { PlanoRecursoDetailComponent } from './plano-recurso-detail.component';
import { PlanoRecursoUpdateComponent } from './plano-recurso-update.component';
import { PlanoRecursoDeleteDialogComponent } from './plano-recurso-delete-dialog.component';
import { planoRecursoRoute } from './plano-recurso.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(planoRecursoRoute)],
  declarations: [PlanoRecursoComponent, PlanoRecursoDetailComponent, PlanoRecursoUpdateComponent, PlanoRecursoDeleteDialogComponent],
  entryComponents: [PlanoRecursoDeleteDialogComponent],
})
export class NucleoPlanoRecursoModule {}

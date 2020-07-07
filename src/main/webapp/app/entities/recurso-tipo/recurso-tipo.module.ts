import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { RecursoTipoComponent } from './recurso-tipo.component';
import { RecursoTipoDetailComponent } from './recurso-tipo-detail.component';
import { RecursoTipoUpdateComponent } from './recurso-tipo-update.component';
import { RecursoTipoDeleteDialogComponent } from './recurso-tipo-delete-dialog.component';
import { recursoTipoRoute } from './recurso-tipo.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(recursoTipoRoute)],
  declarations: [RecursoTipoComponent, RecursoTipoDetailComponent, RecursoTipoUpdateComponent, RecursoTipoDeleteDialogComponent],
  entryComponents: [RecursoTipoDeleteDialogComponent],
})
export class NucleoRecursoTipoModule {}

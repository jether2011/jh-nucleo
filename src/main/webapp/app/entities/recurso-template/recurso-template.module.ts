import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { RecursoTemplateComponent } from './recurso-template.component';
import { RecursoTemplateDetailComponent } from './recurso-template-detail.component';
import { RecursoTemplateUpdateComponent } from './recurso-template-update.component';
import { RecursoTemplateDeleteDialogComponent } from './recurso-template-delete-dialog.component';
import { recursoTemplateRoute } from './recurso-template.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(recursoTemplateRoute)],
  declarations: [
    RecursoTemplateComponent,
    RecursoTemplateDetailComponent,
    RecursoTemplateUpdateComponent,
    RecursoTemplateDeleteDialogComponent,
  ],
  entryComponents: [RecursoTemplateDeleteDialogComponent],
})
export class NucleoRecursoTemplateModule {}

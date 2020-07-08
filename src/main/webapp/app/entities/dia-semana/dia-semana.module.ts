import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { DiaSemanaComponent } from './dia-semana.component';
import { DiaSemanaDetailComponent } from './dia-semana-detail.component';
import { DiaSemanaUpdateComponent } from './dia-semana-update.component';
import { DiaSemanaDeleteDialogComponent } from './dia-semana-delete-dialog.component';
import { diaSemanaRoute } from './dia-semana.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(diaSemanaRoute)],
  declarations: [DiaSemanaComponent, DiaSemanaDetailComponent, DiaSemanaUpdateComponent, DiaSemanaDeleteDialogComponent],
  entryComponents: [DiaSemanaDeleteDialogComponent],
})
export class NucleoDiaSemanaModule {}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { IntegradorComponent } from './integrador.component';
import { IntegradorDetailComponent } from './integrador-detail.component';
import { IntegradorUpdateComponent } from './integrador-update.component';
import { IntegradorDeleteDialogComponent } from './integrador-delete-dialog.component';
import { integradorRoute } from './integrador.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(integradorRoute)],
  declarations: [IntegradorComponent, IntegradorDetailComponent, IntegradorUpdateComponent, IntegradorDeleteDialogComponent],
  entryComponents: [IntegradorDeleteDialogComponent],
})
export class NucleoIntegradorModule {}

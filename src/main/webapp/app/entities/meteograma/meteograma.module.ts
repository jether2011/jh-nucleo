import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { MeteogramaComponent } from './meteograma.component';
import { MeteogramaDetailComponent } from './meteograma-detail.component';
import { MeteogramaUpdateComponent } from './meteograma-update.component';
import { MeteogramaDeleteDialogComponent } from './meteograma-delete-dialog.component';
import { meteogramaRoute } from './meteograma.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(meteogramaRoute)],
  declarations: [MeteogramaComponent, MeteogramaDetailComponent, MeteogramaUpdateComponent, MeteogramaDeleteDialogComponent],
  entryComponents: [MeteogramaDeleteDialogComponent],
})
export class NucleoMeteogramaModule {}

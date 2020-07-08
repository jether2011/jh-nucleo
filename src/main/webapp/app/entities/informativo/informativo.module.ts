import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { InformativoComponent } from './informativo.component';
import { InformativoDetailComponent } from './informativo-detail.component';
import { InformativoUpdateComponent } from './informativo-update.component';
import { InformativoDeleteDialogComponent } from './informativo-delete-dialog.component';
import { informativoRoute } from './informativo.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(informativoRoute)],
  declarations: [InformativoComponent, InformativoDetailComponent, InformativoUpdateComponent, InformativoDeleteDialogComponent],
  entryComponents: [InformativoDeleteDialogComponent],
})
export class NucleoInformativoModule {}

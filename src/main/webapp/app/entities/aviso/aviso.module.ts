import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { AvisoComponent } from './aviso.component';
import { AvisoDetailComponent } from './aviso-detail.component';
import { AvisoUpdateComponent } from './aviso-update.component';
import { AvisoDeleteDialogComponent } from './aviso-delete-dialog.component';
import { avisoRoute } from './aviso.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(avisoRoute)],
  declarations: [AvisoComponent, AvisoDetailComponent, AvisoUpdateComponent, AvisoDeleteDialogComponent],
  entryComponents: [AvisoDeleteDialogComponent],
})
export class NucleoAvisoModule {}

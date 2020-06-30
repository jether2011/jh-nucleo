import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { AvisoTipoComponent } from './aviso-tipo.component';
import { AvisoTipoDetailComponent } from './aviso-tipo-detail.component';
import { AvisoTipoUpdateComponent } from './aviso-tipo-update.component';
import { AvisoTipoDeleteDialogComponent } from './aviso-tipo-delete-dialog.component';
import { avisoTipoRoute } from './aviso-tipo.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(avisoTipoRoute)],
  declarations: [AvisoTipoComponent, AvisoTipoDetailComponent, AvisoTipoUpdateComponent, AvisoTipoDeleteDialogComponent],
  entryComponents: [AvisoTipoDeleteDialogComponent],
})
export class NucleoAvisoTipoModule {}

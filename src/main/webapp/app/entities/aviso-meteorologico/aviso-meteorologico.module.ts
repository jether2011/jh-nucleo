import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { AvisoMeteorologicoComponent } from './aviso-meteorologico.component';
import { AvisoMeteorologicoDetailComponent } from './aviso-meteorologico-detail.component';
import { AvisoMeteorologicoUpdateComponent } from './aviso-meteorologico-update.component';
import { AvisoMeteorologicoDeleteDialogComponent } from './aviso-meteorologico-delete-dialog.component';
import { avisoMeteorologicoRoute } from './aviso-meteorologico.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(avisoMeteorologicoRoute)],
  declarations: [
    AvisoMeteorologicoComponent,
    AvisoMeteorologicoDetailComponent,
    AvisoMeteorologicoUpdateComponent,
    AvisoMeteorologicoDeleteDialogComponent,
  ],
  entryComponents: [AvisoMeteorologicoDeleteDialogComponent],
})
export class NucleoAvisoMeteorologicoModule {}

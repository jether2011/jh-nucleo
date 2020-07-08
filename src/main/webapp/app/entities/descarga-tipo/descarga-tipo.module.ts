import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { DescargaTipoComponent } from './descarga-tipo.component';
import { DescargaTipoDetailComponent } from './descarga-tipo-detail.component';
import { DescargaTipoUpdateComponent } from './descarga-tipo-update.component';
import { DescargaTipoDeleteDialogComponent } from './descarga-tipo-delete-dialog.component';
import { descargaTipoRoute } from './descarga-tipo.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(descargaTipoRoute)],
  declarations: [DescargaTipoComponent, DescargaTipoDetailComponent, DescargaTipoUpdateComponent, DescargaTipoDeleteDialogComponent],
  entryComponents: [DescargaTipoDeleteDialogComponent],
})
export class NucleoDescargaTipoModule {}

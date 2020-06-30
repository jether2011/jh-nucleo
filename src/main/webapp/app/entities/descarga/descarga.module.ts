import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { DescargaComponent } from './descarga.component';
import { DescargaDetailComponent } from './descarga-detail.component';
import { DescargaUpdateComponent } from './descarga-update.component';
import { DescargaDeleteDialogComponent } from './descarga-delete-dialog.component';
import { descargaRoute } from './descarga.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(descargaRoute)],
  declarations: [DescargaComponent, DescargaDetailComponent, DescargaUpdateComponent, DescargaDeleteDialogComponent],
  entryComponents: [DescargaDeleteDialogComponent],
})
export class NucleoDescargaModule {}

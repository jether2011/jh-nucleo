import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { DescargaUnidadeComponent } from './descarga-unidade.component';
import { DescargaUnidadeDetailComponent } from './descarga-unidade-detail.component';
import { DescargaUnidadeUpdateComponent } from './descarga-unidade-update.component';
import { DescargaUnidadeDeleteDialogComponent } from './descarga-unidade-delete-dialog.component';
import { descargaUnidadeRoute } from './descarga-unidade.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(descargaUnidadeRoute)],
  declarations: [
    DescargaUnidadeComponent,
    DescargaUnidadeDetailComponent,
    DescargaUnidadeUpdateComponent,
    DescargaUnidadeDeleteDialogComponent,
  ],
  entryComponents: [DescargaUnidadeDeleteDialogComponent],
})
export class NucleoDescargaUnidadeModule {}

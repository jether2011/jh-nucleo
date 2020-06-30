import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { UmidadeArComponent } from './umidade-ar.component';
import { UmidadeArDetailComponent } from './umidade-ar-detail.component';
import { UmidadeArUpdateComponent } from './umidade-ar-update.component';
import { UmidadeArDeleteDialogComponent } from './umidade-ar-delete-dialog.component';
import { umidadeArRoute } from './umidade-ar.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(umidadeArRoute)],
  declarations: [UmidadeArComponent, UmidadeArDetailComponent, UmidadeArUpdateComponent, UmidadeArDeleteDialogComponent],
  entryComponents: [UmidadeArDeleteDialogComponent],
})
export class NucleoUmidadeArModule {}

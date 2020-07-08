import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { AlvoComponent } from './alvo.component';
import { AlvoDetailComponent } from './alvo-detail.component';
import { AlvoUpdateComponent } from './alvo-update.component';
import { AlvoDeleteDialogComponent } from './alvo-delete-dialog.component';
import { alvoRoute } from './alvo.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(alvoRoute)],
  declarations: [AlvoComponent, AlvoDetailComponent, AlvoUpdateComponent, AlvoDeleteDialogComponent],
  entryComponents: [AlvoDeleteDialogComponent],
})
export class NucleoAlvoModule {}

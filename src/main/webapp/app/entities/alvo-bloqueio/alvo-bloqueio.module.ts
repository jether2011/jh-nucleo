import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { AlvoBloqueioComponent } from './alvo-bloqueio.component';
import { AlvoBloqueioDetailComponent } from './alvo-bloqueio-detail.component';
import { AlvoBloqueioUpdateComponent } from './alvo-bloqueio-update.component';
import { AlvoBloqueioDeleteDialogComponent } from './alvo-bloqueio-delete-dialog.component';
import { alvoBloqueioRoute } from './alvo-bloqueio.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(alvoBloqueioRoute)],
  declarations: [AlvoBloqueioComponent, AlvoBloqueioDetailComponent, AlvoBloqueioUpdateComponent, AlvoBloqueioDeleteDialogComponent],
  entryComponents: [AlvoBloqueioDeleteDialogComponent],
})
export class NucleoAlvoBloqueioModule {}

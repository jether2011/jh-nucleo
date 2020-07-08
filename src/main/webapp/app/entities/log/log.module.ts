import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { LogComponent } from './log.component';
import { LogDetailComponent } from './log-detail.component';
import { LogUpdateComponent } from './log-update.component';
import { LogDeleteDialogComponent } from './log-delete-dialog.component';
import { logRoute } from './log.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(logRoute)],
  declarations: [LogComponent, LogDetailComponent, LogUpdateComponent, LogDeleteDialogComponent],
  entryComponents: [LogDeleteDialogComponent],
})
export class NucleoLogModule {}

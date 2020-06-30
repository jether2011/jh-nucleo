import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { VoceSabiaComponent } from './voce-sabia.component';
import { VoceSabiaDetailComponent } from './voce-sabia-detail.component';
import { VoceSabiaUpdateComponent } from './voce-sabia-update.component';
import { VoceSabiaDeleteDialogComponent } from './voce-sabia-delete-dialog.component';
import { voceSabiaRoute } from './voce-sabia.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(voceSabiaRoute)],
  declarations: [VoceSabiaComponent, VoceSabiaDetailComponent, VoceSabiaUpdateComponent, VoceSabiaDeleteDialogComponent],
  entryComponents: [VoceSabiaDeleteDialogComponent],
})
export class NucleoVoceSabiaModule {}

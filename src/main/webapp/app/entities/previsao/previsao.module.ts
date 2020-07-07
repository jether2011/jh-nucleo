import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { PrevisaoComponent } from './previsao.component';
import { PrevisaoDetailComponent } from './previsao-detail.component';
import { PrevisaoUpdateComponent } from './previsao-update.component';
import { PrevisaoDeleteDialogComponent } from './previsao-delete-dialog.component';
import { previsaoRoute } from './previsao.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(previsaoRoute)],
  declarations: [PrevisaoComponent, PrevisaoDetailComponent, PrevisaoUpdateComponent, PrevisaoDeleteDialogComponent],
  entryComponents: [PrevisaoDeleteDialogComponent],
})
export class NucleoPrevisaoModule {}

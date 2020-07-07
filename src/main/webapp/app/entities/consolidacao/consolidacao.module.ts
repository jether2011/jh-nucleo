import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { ConsolidacaoComponent } from './consolidacao.component';
import { ConsolidacaoDetailComponent } from './consolidacao-detail.component';
import { ConsolidacaoUpdateComponent } from './consolidacao-update.component';
import { ConsolidacaoDeleteDialogComponent } from './consolidacao-delete-dialog.component';
import { consolidacaoRoute } from './consolidacao.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(consolidacaoRoute)],
  declarations: [ConsolidacaoComponent, ConsolidacaoDetailComponent, ConsolidacaoUpdateComponent, ConsolidacaoDeleteDialogComponent],
  entryComponents: [ConsolidacaoDeleteDialogComponent],
})
export class NucleoConsolidacaoModule {}

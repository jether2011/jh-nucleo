import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { CondicaoTempoComponent } from './condicao-tempo.component';
import { CondicaoTempoDetailComponent } from './condicao-tempo-detail.component';
import { CondicaoTempoUpdateComponent } from './condicao-tempo-update.component';
import { CondicaoTempoDeleteDialogComponent } from './condicao-tempo-delete-dialog.component';
import { condicaoTempoRoute } from './condicao-tempo.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(condicaoTempoRoute)],
  declarations: [CondicaoTempoComponent, CondicaoTempoDetailComponent, CondicaoTempoUpdateComponent, CondicaoTempoDeleteDialogComponent],
  entryComponents: [CondicaoTempoDeleteDialogComponent],
})
export class NucleoCondicaoTempoModule {}

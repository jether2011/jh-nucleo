import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { TempestadeProbabilidadeComponent } from './tempestade-probabilidade.component';
import { TempestadeProbabilidadeDetailComponent } from './tempestade-probabilidade-detail.component';
import { TempestadeProbabilidadeUpdateComponent } from './tempestade-probabilidade-update.component';
import { TempestadeProbabilidadeDeleteDialogComponent } from './tempestade-probabilidade-delete-dialog.component';
import { tempestadeProbabilidadeRoute } from './tempestade-probabilidade.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(tempestadeProbabilidadeRoute)],
  declarations: [
    TempestadeProbabilidadeComponent,
    TempestadeProbabilidadeDetailComponent,
    TempestadeProbabilidadeUpdateComponent,
    TempestadeProbabilidadeDeleteDialogComponent,
  ],
  entryComponents: [TempestadeProbabilidadeDeleteDialogComponent],
})
export class NucleoTempestadeProbabilidadeModule {}

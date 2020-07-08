import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { TempestadeNivelComponent } from './tempestade-nivel.component';
import { TempestadeNivelDetailComponent } from './tempestade-nivel-detail.component';
import { TempestadeNivelUpdateComponent } from './tempestade-nivel-update.component';
import { TempestadeNivelDeleteDialogComponent } from './tempestade-nivel-delete-dialog.component';
import { tempestadeNivelRoute } from './tempestade-nivel.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(tempestadeNivelRoute)],
  declarations: [
    TempestadeNivelComponent,
    TempestadeNivelDetailComponent,
    TempestadeNivelUpdateComponent,
    TempestadeNivelDeleteDialogComponent,
  ],
  entryComponents: [TempestadeNivelDeleteDialogComponent],
})
export class NucleoTempestadeNivelModule {}

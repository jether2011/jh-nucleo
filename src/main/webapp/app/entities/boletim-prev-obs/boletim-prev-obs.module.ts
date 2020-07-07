import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { BoletimPrevObsComponent } from './boletim-prev-obs.component';
import { BoletimPrevObsDetailComponent } from './boletim-prev-obs-detail.component';
import { BoletimPrevObsUpdateComponent } from './boletim-prev-obs-update.component';
import { BoletimPrevObsDeleteDialogComponent } from './boletim-prev-obs-delete-dialog.component';
import { boletimPrevObsRoute } from './boletim-prev-obs.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(boletimPrevObsRoute)],
  declarations: [
    BoletimPrevObsComponent,
    BoletimPrevObsDetailComponent,
    BoletimPrevObsUpdateComponent,
    BoletimPrevObsDeleteDialogComponent,
  ],
  entryComponents: [BoletimPrevObsDeleteDialogComponent],
})
export class NucleoBoletimPrevObsModule {}

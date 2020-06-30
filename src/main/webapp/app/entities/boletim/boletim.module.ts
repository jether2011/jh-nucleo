import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { BoletimComponent } from './boletim.component';
import { BoletimDetailComponent } from './boletim-detail.component';
import { BoletimUpdateComponent } from './boletim-update.component';
import { BoletimDeleteDialogComponent } from './boletim-delete-dialog.component';
import { boletimRoute } from './boletim.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(boletimRoute)],
  declarations: [BoletimComponent, BoletimDetailComponent, BoletimUpdateComponent, BoletimDeleteDialogComponent],
  entryComponents: [BoletimDeleteDialogComponent],
})
export class NucleoBoletimModule {}

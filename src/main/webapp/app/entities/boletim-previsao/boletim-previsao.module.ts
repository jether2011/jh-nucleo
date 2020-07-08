import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { BoletimPrevisaoComponent } from './boletim-previsao.component';
import { BoletimPrevisaoDetailComponent } from './boletim-previsao-detail.component';
import { BoletimPrevisaoUpdateComponent } from './boletim-previsao-update.component';
import { BoletimPrevisaoDeleteDialogComponent } from './boletim-previsao-delete-dialog.component';
import { boletimPrevisaoRoute } from './boletim-previsao.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(boletimPrevisaoRoute)],
  declarations: [
    BoletimPrevisaoComponent,
    BoletimPrevisaoDetailComponent,
    BoletimPrevisaoUpdateComponent,
    BoletimPrevisaoDeleteDialogComponent,
  ],
  entryComponents: [BoletimPrevisaoDeleteDialogComponent],
})
export class NucleoBoletimPrevisaoModule {}

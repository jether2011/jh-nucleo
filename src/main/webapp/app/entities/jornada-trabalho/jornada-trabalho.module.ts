import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { JornadaTrabalhoComponent } from './jornada-trabalho.component';
import { JornadaTrabalhoDetailComponent } from './jornada-trabalho-detail.component';
import { JornadaTrabalhoUpdateComponent } from './jornada-trabalho-update.component';
import { JornadaTrabalhoDeleteDialogComponent } from './jornada-trabalho-delete-dialog.component';
import { jornadaTrabalhoRoute } from './jornada-trabalho.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(jornadaTrabalhoRoute)],
  declarations: [
    JornadaTrabalhoComponent,
    JornadaTrabalhoDetailComponent,
    JornadaTrabalhoUpdateComponent,
    JornadaTrabalhoDeleteDialogComponent,
  ],
  entryComponents: [JornadaTrabalhoDeleteDialogComponent],
})
export class NucleoJornadaTrabalhoModule {}

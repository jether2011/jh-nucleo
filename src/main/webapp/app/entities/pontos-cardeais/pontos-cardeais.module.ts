import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { PontosCardeaisComponent } from './pontos-cardeais.component';
import { PontosCardeaisDetailComponent } from './pontos-cardeais-detail.component';
import { PontosCardeaisUpdateComponent } from './pontos-cardeais-update.component';
import { PontosCardeaisDeleteDialogComponent } from './pontos-cardeais-delete-dialog.component';
import { pontosCardeaisRoute } from './pontos-cardeais.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(pontosCardeaisRoute)],
  declarations: [
    PontosCardeaisComponent,
    PontosCardeaisDetailComponent,
    PontosCardeaisUpdateComponent,
    PontosCardeaisDeleteDialogComponent,
  ],
  entryComponents: [PontosCardeaisDeleteDialogComponent],
})
export class NucleoPontosCardeaisModule {}

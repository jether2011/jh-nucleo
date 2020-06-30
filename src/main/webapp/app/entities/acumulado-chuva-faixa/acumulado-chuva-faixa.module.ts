import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { AcumuladoChuvaFaixaComponent } from './acumulado-chuva-faixa.component';
import { AcumuladoChuvaFaixaDetailComponent } from './acumulado-chuva-faixa-detail.component';
import { AcumuladoChuvaFaixaUpdateComponent } from './acumulado-chuva-faixa-update.component';
import { AcumuladoChuvaFaixaDeleteDialogComponent } from './acumulado-chuva-faixa-delete-dialog.component';
import { acumuladoChuvaFaixaRoute } from './acumulado-chuva-faixa.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(acumuladoChuvaFaixaRoute)],
  declarations: [
    AcumuladoChuvaFaixaComponent,
    AcumuladoChuvaFaixaDetailComponent,
    AcumuladoChuvaFaixaUpdateComponent,
    AcumuladoChuvaFaixaDeleteDialogComponent,
  ],
  entryComponents: [AcumuladoChuvaFaixaDeleteDialogComponent],
})
export class NucleoAcumuladoChuvaFaixaModule {}

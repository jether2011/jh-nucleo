import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { VariavelMeteorologicaComponent } from './variavel-meteorologica.component';
import { VariavelMeteorologicaDetailComponent } from './variavel-meteorologica-detail.component';
import { VariavelMeteorologicaUpdateComponent } from './variavel-meteorologica-update.component';
import { VariavelMeteorologicaDeleteDialogComponent } from './variavel-meteorologica-delete-dialog.component';
import { variavelMeteorologicaRoute } from './variavel-meteorologica.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(variavelMeteorologicaRoute)],
  declarations: [
    VariavelMeteorologicaComponent,
    VariavelMeteorologicaDetailComponent,
    VariavelMeteorologicaUpdateComponent,
    VariavelMeteorologicaDeleteDialogComponent,
  ],
  entryComponents: [VariavelMeteorologicaDeleteDialogComponent],
})
export class NucleoVariavelMeteorologicaModule {}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { AlertaFerramentaComponent } from './alerta-ferramenta.component';
import { AlertaFerramentaDetailComponent } from './alerta-ferramenta-detail.component';
import { AlertaFerramentaUpdateComponent } from './alerta-ferramenta-update.component';
import { AlertaFerramentaDeleteDialogComponent } from './alerta-ferramenta-delete-dialog.component';
import { alertaFerramentaRoute } from './alerta-ferramenta.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(alertaFerramentaRoute)],
  declarations: [
    AlertaFerramentaComponent,
    AlertaFerramentaDetailComponent,
    AlertaFerramentaUpdateComponent,
    AlertaFerramentaDeleteDialogComponent,
  ],
  entryComponents: [AlertaFerramentaDeleteDialogComponent],
})
export class NucleoAlertaFerramentaModule {}

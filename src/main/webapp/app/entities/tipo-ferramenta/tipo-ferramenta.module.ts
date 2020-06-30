import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { TipoFerramentaComponent } from './tipo-ferramenta.component';
import { TipoFerramentaDetailComponent } from './tipo-ferramenta-detail.component';
import { TipoFerramentaUpdateComponent } from './tipo-ferramenta-update.component';
import { TipoFerramentaDeleteDialogComponent } from './tipo-ferramenta-delete-dialog.component';
import { tipoFerramentaRoute } from './tipo-ferramenta.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(tipoFerramentaRoute)],
  declarations: [
    TipoFerramentaComponent,
    TipoFerramentaDetailComponent,
    TipoFerramentaUpdateComponent,
    TipoFerramentaDeleteDialogComponent,
  ],
  entryComponents: [TipoFerramentaDeleteDialogComponent],
})
export class NucleoTipoFerramentaModule {}

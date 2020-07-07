import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { FerramentaComponent } from './ferramenta.component';
import { FerramentaDetailComponent } from './ferramenta-detail.component';
import { FerramentaUpdateComponent } from './ferramenta-update.component';
import { FerramentaDeleteDialogComponent } from './ferramenta-delete-dialog.component';
import { ferramentaRoute } from './ferramenta.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(ferramentaRoute)],
  declarations: [FerramentaComponent, FerramentaDetailComponent, FerramentaUpdateComponent, FerramentaDeleteDialogComponent],
  entryComponents: [FerramentaDeleteDialogComponent],
})
export class NucleoFerramentaModule {}

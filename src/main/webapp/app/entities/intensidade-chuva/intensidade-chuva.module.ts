import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { IntensidadeChuvaComponent } from './intensidade-chuva.component';
import { IntensidadeChuvaDetailComponent } from './intensidade-chuva-detail.component';
import { IntensidadeChuvaUpdateComponent } from './intensidade-chuva-update.component';
import { IntensidadeChuvaDeleteDialogComponent } from './intensidade-chuva-delete-dialog.component';
import { intensidadeChuvaRoute } from './intensidade-chuva.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(intensidadeChuvaRoute)],
  declarations: [
    IntensidadeChuvaComponent,
    IntensidadeChuvaDetailComponent,
    IntensidadeChuvaUpdateComponent,
    IntensidadeChuvaDeleteDialogComponent,
  ],
  entryComponents: [IntensidadeChuvaDeleteDialogComponent],
})
export class NucleoIntensidadeChuvaModule {}

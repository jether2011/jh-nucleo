import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { BoletimPrevVariavelMetComponent } from './boletim-prev-variavel-met.component';
import { BoletimPrevVariavelMetDetailComponent } from './boletim-prev-variavel-met-detail.component';
import { BoletimPrevVariavelMetUpdateComponent } from './boletim-prev-variavel-met-update.component';
import { BoletimPrevVariavelMetDeleteDialogComponent } from './boletim-prev-variavel-met-delete-dialog.component';
import { boletimPrevVariavelMetRoute } from './boletim-prev-variavel-met.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(boletimPrevVariavelMetRoute)],
  declarations: [
    BoletimPrevVariavelMetComponent,
    BoletimPrevVariavelMetDetailComponent,
    BoletimPrevVariavelMetUpdateComponent,
    BoletimPrevVariavelMetDeleteDialogComponent,
  ],
  entryComponents: [BoletimPrevVariavelMetDeleteDialogComponent],
})
export class NucleoBoletimPrevVariavelMetModule {}

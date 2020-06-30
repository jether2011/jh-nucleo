import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { ContatoComponent } from './contato.component';
import { ContatoDetailComponent } from './contato-detail.component';
import { ContatoUpdateComponent } from './contato-update.component';
import { ContatoDeleteDialogComponent } from './contato-delete-dialog.component';
import { contatoRoute } from './contato.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(contatoRoute)],
  declarations: [ContatoComponent, ContatoDetailComponent, ContatoUpdateComponent, ContatoDeleteDialogComponent],
  entryComponents: [ContatoDeleteDialogComponent],
})
export class NucleoContatoModule {}

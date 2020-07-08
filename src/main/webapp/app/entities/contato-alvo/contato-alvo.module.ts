import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { ContatoAlvoComponent } from './contato-alvo.component';
import { ContatoAlvoDetailComponent } from './contato-alvo-detail.component';
import { ContatoAlvoUpdateComponent } from './contato-alvo-update.component';
import { ContatoAlvoDeleteDialogComponent } from './contato-alvo-delete-dialog.component';
import { contatoAlvoRoute } from './contato-alvo.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(contatoAlvoRoute)],
  declarations: [ContatoAlvoComponent, ContatoAlvoDetailComponent, ContatoAlvoUpdateComponent, ContatoAlvoDeleteDialogComponent],
  entryComponents: [ContatoAlvoDeleteDialogComponent],
})
export class NucleoContatoAlvoModule {}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { NotificacaoEnviadaComponent } from './notificacao-enviada.component';
import { NotificacaoEnviadaDetailComponent } from './notificacao-enviada-detail.component';
import { NotificacaoEnviadaUpdateComponent } from './notificacao-enviada-update.component';
import { NotificacaoEnviadaDeleteDialogComponent } from './notificacao-enviada-delete-dialog.component';
import { notificacaoEnviadaRoute } from './notificacao-enviada.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(notificacaoEnviadaRoute)],
  declarations: [
    NotificacaoEnviadaComponent,
    NotificacaoEnviadaDetailComponent,
    NotificacaoEnviadaUpdateComponent,
    NotificacaoEnviadaDeleteDialogComponent,
  ],
  entryComponents: [NotificacaoEnviadaDeleteDialogComponent],
})
export class NucleoNotificacaoEnviadaModule {}

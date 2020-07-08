import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { UsuarioPerfilComponent } from './usuario-perfil.component';
import { UsuarioPerfilDetailComponent } from './usuario-perfil-detail.component';
import { UsuarioPerfilUpdateComponent } from './usuario-perfil-update.component';
import { UsuarioPerfilDeleteDialogComponent } from './usuario-perfil-delete-dialog.component';
import { usuarioPerfilRoute } from './usuario-perfil.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(usuarioPerfilRoute)],
  declarations: [UsuarioPerfilComponent, UsuarioPerfilDetailComponent, UsuarioPerfilUpdateComponent, UsuarioPerfilDeleteDialogComponent],
  entryComponents: [UsuarioPerfilDeleteDialogComponent],
})
export class NucleoUsuarioPerfilModule {}

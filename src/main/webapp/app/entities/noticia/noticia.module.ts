import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NucleoSharedModule } from 'app/shared/shared.module';
import { NoticiaComponent } from './noticia.component';
import { NoticiaDetailComponent } from './noticia-detail.component';
import { NoticiaUpdateComponent } from './noticia-update.component';
import { NoticiaDeleteDialogComponent } from './noticia-delete-dialog.component';
import { noticiaRoute } from './noticia.route';

@NgModule({
  imports: [NucleoSharedModule, RouterModule.forChild(noticiaRoute)],
  declarations: [NoticiaComponent, NoticiaDetailComponent, NoticiaUpdateComponent, NoticiaDeleteDialogComponent],
  entryComponents: [NoticiaDeleteDialogComponent],
})
export class NucleoNoticiaModule {}

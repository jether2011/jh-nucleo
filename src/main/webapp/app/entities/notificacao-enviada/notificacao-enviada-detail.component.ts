import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INotificacaoEnviada } from 'app/shared/model/notificacao-enviada.model';

@Component({
  selector: 'jhi-notificacao-enviada-detail',
  templateUrl: './notificacao-enviada-detail.component.html',
})
export class NotificacaoEnviadaDetailComponent implements OnInit {
  notificacaoEnviada: INotificacaoEnviada | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notificacaoEnviada }) => (this.notificacaoEnviada = notificacaoEnviada));
  }

  previousState(): void {
    window.history.back();
  }
}

import { Moment } from 'moment';

export interface IAlvo {
  id?: number;
  nome?: string;
  nomeReduzido?: string;
  descricao?: string;
  primeiroPonto?: string;
  ultimoPonto?: string;
  horarioLiberacao?: Moment;
  horario?: Moment;
  duracao?: string;
  duracaoAtual?: string;
  dataDesativado?: Moment;
  coordenadasAlertaPontos?: string;
  coordenadasLiberacaoPontos?: string;
  telegramTokenBot?: string;
  telegramChatId?: string;
  horarioBloqueioNotificacao?: Moment;
  coordenadasOriginalPontos?: string;
  ativo?: boolean;
  created?: Moment;
  updated?: Moment;
  planoId?: number;
}

export class Alvo implements IAlvo {
  constructor(
    public id?: number,
    public nome?: string,
    public nomeReduzido?: string,
    public descricao?: string,
    public primeiroPonto?: string,
    public ultimoPonto?: string,
    public horarioLiberacao?: Moment,
    public horario?: Moment,
    public duracao?: string,
    public duracaoAtual?: string,
    public dataDesativado?: Moment,
    public coordenadasAlertaPontos?: string,
    public coordenadasLiberacaoPontos?: string,
    public telegramTokenBot?: string,
    public telegramChatId?: string,
    public horarioBloqueioNotificacao?: Moment,
    public coordenadasOriginalPontos?: string,
    public ativo?: boolean,
    public created?: Moment,
    public updated?: Moment,
    public planoId?: number
  ) {
    this.ativo = this.ativo || false;
  }
}

import { Moment } from 'moment';

export interface INotificacaoEnviada {
  id?: number;
  name?: string;
  descricao?: string;
  destinatarios?: string;
  tipo?: string;
  status?: string;
  assunto?: string;
  enviado?: number;
  contador?: number;
  amazonMessageId?: string;
  amazonDateLog?: Moment;
  priceInUsd?: number;
  amazonResposta?: string;
  referenceId?: number;
  created?: Moment;
  updated?: Moment;
  planoRecursoId?: number;
}

export class NotificacaoEnviada implements INotificacaoEnviada {
  constructor(
    public id?: number,
    public name?: string,
    public descricao?: string,
    public destinatarios?: string,
    public tipo?: string,
    public status?: string,
    public assunto?: string,
    public enviado?: number,
    public contador?: number,
    public amazonMessageId?: string,
    public amazonDateLog?: Moment,
    public priceInUsd?: number,
    public amazonResposta?: string,
    public referenceId?: number,
    public created?: Moment,
    public updated?: Moment,
    public planoRecursoId?: number
  ) {}
}

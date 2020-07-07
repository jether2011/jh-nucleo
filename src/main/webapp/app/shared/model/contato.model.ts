import { Moment } from 'moment';

export interface IContato {
  id?: number;
  nome?: string;
  descricao?: string;
  email?: string;
  celular?: string;
  ativo?: boolean;
  contatoAlertaTelefonico?: boolean;
  prioridade?: number;
  horaLigacaoInicial?: string;
  horaLigacaoFinal?: string;
  created?: Moment;
  updated?: Moment;
}

export class Contato implements IContato {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public email?: string,
    public celular?: string,
    public ativo?: boolean,
    public contatoAlertaTelefonico?: boolean,
    public prioridade?: number,
    public horaLigacaoInicial?: string,
    public horaLigacaoFinal?: string,
    public created?: Moment,
    public updated?: Moment
  ) {
    this.ativo = this.ativo || false;
    this.contatoAlertaTelefonico = this.contatoAlertaTelefonico || false;
  }
}

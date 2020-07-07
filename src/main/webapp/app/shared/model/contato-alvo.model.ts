import { Moment } from 'moment';

export interface IContatoAlvo {
  id?: number;
  nome?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
  contatoId?: number;
  alvoId?: number;
}

export class ContatoAlvo implements IContatoAlvo {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public created?: Moment,
    public updated?: Moment,
    public contatoId?: number,
    public alvoId?: number
  ) {}
}

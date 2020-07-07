import { Moment } from 'moment';

export interface ITempestadeProbabilidade {
  id?: number;
  name?: string;
  descricao?: string;
  faixa?: string;
  created?: Moment;
  updated?: Moment;
}

export class TempestadeProbabilidade implements ITempestadeProbabilidade {
  constructor(
    public id?: number,
    public name?: string,
    public descricao?: string,
    public faixa?: string,
    public created?: Moment,
    public updated?: Moment
  ) {}
}

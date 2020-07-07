import { Moment } from 'moment';

export interface IAlvoBloqueio {
  id?: number;
  nome?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
  alvoId?: number;
}

export class AlvoBloqueio implements IAlvoBloqueio {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public created?: Moment,
    public updated?: Moment,
    public alvoId?: number
  ) {}
}

import { Moment } from 'moment';

export interface ICondicaoTempo {
  id?: number;
  nome?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
}

export class CondicaoTempo implements ICondicaoTempo {
  constructor(public id?: number, public nome?: string, public descricao?: string, public created?: Moment, public updated?: Moment) {}
}

import { Moment } from 'moment';

export interface IRede {
  id?: number;
  name?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
}

export class Rede implements IRede {
  constructor(public id?: number, public name?: string, public descricao?: string, public created?: Moment, public updated?: Moment) {}
}

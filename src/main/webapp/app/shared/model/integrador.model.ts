import { Moment } from 'moment';

export interface IIntegrador {
  id?: number;
  nome?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
}

export class Integrador implements IIntegrador {
  constructor(public id?: number, public nome?: string, public descricao?: string, public created?: Moment, public updated?: Moment) {}
}

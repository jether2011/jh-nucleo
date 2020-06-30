import { Moment } from 'moment';

export interface IDescargaTipo {
  id?: number;
  nome?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
}

export class DescargaTipo implements IDescargaTipo {
  constructor(public id?: number, public nome?: string, public descricao?: string, public created?: Moment, public updated?: Moment) {}
}

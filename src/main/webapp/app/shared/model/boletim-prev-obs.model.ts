import { Moment } from 'moment';

export interface IBoletimPrevObs {
  id?: number;
  nome?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
}

export class BoletimPrevObs implements IBoletimPrevObs {
  constructor(public id?: number, public nome?: string, public descricao?: string, public created?: Moment, public updated?: Moment) {}
}

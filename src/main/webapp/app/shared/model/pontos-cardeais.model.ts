import { Moment } from 'moment';

export interface IPontosCardeais {
  id?: number;
  name?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
}

export class PontosCardeais implements IPontosCardeais {
  constructor(public id?: number, public name?: string, public descricao?: string, public created?: Moment, public updated?: Moment) {}
}

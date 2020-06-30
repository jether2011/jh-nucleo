import { Moment } from 'moment';

export interface IPlanoStatus {
  id?: number;
  name?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
}

export class PlanoStatus implements IPlanoStatus {
  constructor(public id?: number, public name?: string, public descricao?: string, public created?: Moment, public updated?: Moment) {}
}

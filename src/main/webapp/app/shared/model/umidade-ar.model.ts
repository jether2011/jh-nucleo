import { Moment } from 'moment';

export interface IUmidadeAr {
  id?: number;
  name?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
}

export class UmidadeAr implements IUmidadeAr {
  constructor(public id?: number, public name?: string, public descricao?: string, public created?: Moment, public updated?: Moment) {}
}

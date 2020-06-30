import { Moment } from 'moment';

export interface IVariavelMeteorologica {
  id?: number;
  name?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
}

export class VariavelMeteorologica implements IVariavelMeteorologica {
  constructor(public id?: number, public name?: string, public descricao?: string, public created?: Moment, public updated?: Moment) {}
}

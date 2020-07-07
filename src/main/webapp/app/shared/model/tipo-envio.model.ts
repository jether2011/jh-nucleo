import { Moment } from 'moment';

export interface ITipoEnvio {
  id?: number;
  name?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
}

export class TipoEnvio implements ITipoEnvio {
  constructor(public id?: number, public name?: string, public descricao?: string, public created?: Moment, public updated?: Moment) {}
}

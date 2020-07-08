import { Moment } from 'moment';

export interface ITipoFerramenta {
  id?: number;
  name?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
}

export class TipoFerramenta implements ITipoFerramenta {
  constructor(public id?: number, public name?: string, public descricao?: string, public created?: Moment, public updated?: Moment) {}
}

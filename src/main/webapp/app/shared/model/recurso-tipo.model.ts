import { Moment } from 'moment';

export interface IRecursoTipo {
  id?: number;
  name?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
}

export class RecursoTipo implements IRecursoTipo {
  constructor(public id?: number, public name?: string, public descricao?: string, public created?: Moment, public updated?: Moment) {}
}

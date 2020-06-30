import { Moment } from 'moment';

export interface IAlertaTipo {
  id?: number;
  nome?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
}

export class AlertaTipo implements IAlertaTipo {
  constructor(public id?: number, public nome?: string, public descricao?: string, public created?: Moment, public updated?: Moment) {}
}

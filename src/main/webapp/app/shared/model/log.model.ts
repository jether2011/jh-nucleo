import { Moment } from 'moment';

export interface ILog {
  id?: number;
  messagem?: string;
  created?: Moment;
  updated?: Moment;
}

export class Log implements ILog {
  constructor(public id?: number, public messagem?: string, public created?: Moment, public updated?: Moment) {}
}

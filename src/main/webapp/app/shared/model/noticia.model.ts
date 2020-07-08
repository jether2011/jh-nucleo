import { Moment } from 'moment';

export interface INoticia {
  id?: number;
  name?: string;
  texto?: string;
  enviado?: boolean;
  created?: Moment;
  updated?: Moment;
}

export class Noticia implements INoticia {
  constructor(
    public id?: number,
    public name?: string,
    public texto?: string,
    public enviado?: boolean,
    public created?: Moment,
    public updated?: Moment
  ) {
    this.enviado = this.enviado || false;
  }
}

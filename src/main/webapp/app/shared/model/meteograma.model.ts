import { Moment } from 'moment';

export interface IMeteograma {
  id?: number;
  name?: string;
  descricao?: string;
  arquivo?: string;
  folder?: string;
  tipoarquivo?: string;
  created?: Moment;
  updated?: Moment;
  planoId?: number;
}

export class Meteograma implements IMeteograma {
  constructor(
    public id?: number,
    public name?: string,
    public descricao?: string,
    public arquivo?: string,
    public folder?: string,
    public tipoarquivo?: string,
    public created?: Moment,
    public updated?: Moment,
    public planoId?: number
  ) {}
}

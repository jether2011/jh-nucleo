import { Moment } from 'moment';

export interface IIntensidadeChuva {
  id?: number;
  nome?: string;
  descricao?: string;
  faixa?: string;
  created?: Moment;
  updated?: Moment;
}

export class IntensidadeChuva implements IIntensidadeChuva {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public faixa?: string,
    public created?: Moment,
    public updated?: Moment
  ) {}
}

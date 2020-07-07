import { Moment } from 'moment';

export interface IAcumuladoChuvaFaixa {
  id?: number;
  nome?: string;
  descricao?: string;
  deMm?: number;
  ateMm?: number;
  created?: Moment;
  updated?: Moment;
}

export class AcumuladoChuvaFaixa implements IAcumuladoChuvaFaixa {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public deMm?: number,
    public ateMm?: number,
    public created?: Moment,
    public updated?: Moment
  ) {}
}

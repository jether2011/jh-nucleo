import { Moment } from 'moment';

export interface IDescargaUnidade {
  id?: number;
  nome?: string;
  descricao?: string;
  unidade?: number;
  created?: Moment;
  updated?: Moment;
}

export class DescargaUnidade implements IDescargaUnidade {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public unidade?: number,
    public created?: Moment,
    public updated?: Moment
  ) {}
}

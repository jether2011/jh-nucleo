import { Moment } from 'moment';

export interface IVentoVmFaixa {
  id?: number;
  name?: string;
  descricao?: string;
  de?: number;
  ate?: number;
  created?: Moment;
  updated?: Moment;
}

export class VentoVmFaixa implements IVentoVmFaixa {
  constructor(
    public id?: number,
    public name?: string,
    public descricao?: string,
    public de?: number,
    public ate?: number,
    public created?: Moment,
    public updated?: Moment
  ) {}
}

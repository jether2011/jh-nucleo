import { Moment } from 'moment';

export interface IPlanoRede {
  id?: number;
  name?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
  planoId?: number;
  redeId?: number;
}

export class PlanoRede implements IPlanoRede {
  constructor(
    public id?: number,
    public name?: string,
    public descricao?: string,
    public created?: Moment,
    public updated?: Moment,
    public planoId?: number,
    public redeId?: number
  ) {}
}

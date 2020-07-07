import { Moment } from 'moment';

export interface IPlanoLayer {
  id?: number;
  name?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
  planoId?: number;
  layerId?: number;
  alvoId?: number;
}

export class PlanoLayer implements IPlanoLayer {
  constructor(
    public id?: number,
    public name?: string,
    public descricao?: string,
    public created?: Moment,
    public updated?: Moment,
    public planoId?: number,
    public layerId?: number,
    public alvoId?: number
  ) {}
}

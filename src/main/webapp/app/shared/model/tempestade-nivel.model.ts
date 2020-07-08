import { Moment } from 'moment';

export interface ITempestadeNivel {
  id?: number;
  name?: string;
  descricao?: string;
  taxaDeRaios?: string;
  ventosVelocidade?: string;
  granizo?: string;
  potencialDeDanos?: string;
  created?: Moment;
  updated?: Moment;
  intensidadeChuvaId?: number;
}

export class TempestadeNivel implements ITempestadeNivel {
  constructor(
    public id?: number,
    public name?: string,
    public descricao?: string,
    public taxaDeRaios?: string,
    public ventosVelocidade?: string,
    public granizo?: string,
    public potencialDeDanos?: string,
    public created?: Moment,
    public updated?: Moment,
    public intensidadeChuvaId?: number
  ) {}
}

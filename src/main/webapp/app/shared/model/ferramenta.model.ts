import { Moment } from 'moment';

export interface IFerramenta {
  id?: number;
  nome?: string;
  descricao?: string;
  created?: Moment;
  updated?: number;
  tipoFerramentaId?: number;
}

export class Ferramenta implements IFerramenta {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public created?: Moment,
    public updated?: number,
    public tipoFerramentaId?: number
  ) {}
}
